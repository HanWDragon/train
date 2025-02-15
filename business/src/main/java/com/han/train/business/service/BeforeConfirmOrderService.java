package com.han.train.business.service;


import cn.hutool.core.date.DateUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.esotericsoftware.minlog.Log;
import com.han.train.business.enums.RedisKeyPreEnum;
import com.han.train.business.mapper.ConfirmOrderMapper;
import com.han.train.business.request.ConfirmOrderDoReq;
import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.exception.BusinessException;
import com.han.train.common.exception.BusinessExceptionEnum;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BeforeConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeConfirmOrderService.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Autowired
    private SkTokenService skTokenService;

    @Autowired
    private RedissonClient redissonClient;

    @Resource
    public RocketMQTemplate rocketMQTemplate;

    @Resource
    private ConfirmOrderService confirmOrderService;

    @SentinelResource(value = "beforeDoConfirm", blockHandler = "beforeDoConfirmBlock")
    public void beforeDoConfirm(ConfirmOrderDoReq req) {

        // 校验令牌余量
        boolean validSkToken = skTokenService.validSkToken(req.getDate(), req.getTrainCode(), LoginMemberContext.getId());
        if (validSkToken) {
            LOG.info("令牌校验通过");
        } else {
            LOG.info("令牌校验不通过");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_SK_TOKEN_FAIL);
        }


        // 多个人抢同一个车次，这个场景需要加锁，但是多个人抢不同车次，其实是不相关的
        String key = RedisKeyPreEnum.CONFIRM_ORDER + "-" + DateUtil.formatDate(req.getDate()) + "-" + req.getTrainCode();


//        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(key, "1", 1, TimeUnit.SECONDS);
//        if (Boolean.TRUE.equals(ifAbsent)) {
//            LOG.info("恭喜，抢到锁了");
//        } else {
//            // 只是没有抢到锁，并不是没有票，所以需要提示请重试
//            LOG.info("很遗憾，没抢到锁");
//            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
//        }

        RLock lock = null;

        try {
            lock = redissonClient.getLock(key);
            /**
             waitTime – the maximum time to acquire the lock 等待获取锁时间(最大尝试获得锁的时间)，超时返回false
             leaseTime – lease time 锁时长，即n秒后自动释放锁
             time unit – time unit 时间单位
             */
            // boolean tryLock = lock.tryLock(30, 10, TimeUnit.SECONDS); // 不带看门狗
            boolean tryLock = lock.tryLock(0, TimeUnit.SECONDS); // 带看门狗
            if (tryLock) {
                LOG.info("恭喜，抢到锁了！");
                // 可以把下面这段放开，只用一个线程来测试，看看redisson的看门狗效果
                // for (int i = 0; i < 30; i++) {
                //     Long expire = redisTemplate.opsForValue().getOperations().getExpire(lockKey);
                //     LOG.info("锁过期时间还有：{}", expire);
                //     Thread.sleep(1000);
                // }
            } else {
                // 只是没抢到锁，并不知道票抢完了没，所以提示稍候再试
                LOG.info("很遗憾，没抢到锁");
                throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
            }

            //TODO 准备 MQ 相关代码
            LOG.info("准备发送信息到MQ，等待出票");


        } catch (InterruptedException e) {
            Log.error("购票异常", e);
        } finally {
            LOG.info("购票结束，释放锁，Key:{}", key);
            if (null != lock && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

    }

    /**
     * 降级方法，需包含限流方法的所有参数和BlockException参数
     *
     * @param req
     * @param e
     */
    public void beforeDoConfirmBlock(ConfirmOrderDoReq req, BlockException e) {
        LOG.info("购票请求被限流：{}", req);
        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_FLOW_EXCEPTION);
    }
}
