package com.han.train.business.mq;

import com.alibaba.fastjson2.JSON;
import com.han.train.business.request.ConfirmOrderDoReq;
import com.han.train.business.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "default", topic = "CONFIRM_ORDER")
public class ConfirmOrderConsumer implements RocketMQListener<MessageExt> {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderConsumer.class);

    @Resource
    private ConfirmOrderService confirmOrderService;

    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        ConfirmOrderDoReq req = JSON.parseObject(new String(body), ConfirmOrderDoReq.class);
//         MDC.put("LOG_ID", dto.getLogId());
        LOG.info("ROCKETMQ收到消息：{}", new String(body));
        confirmOrderService.doConfirm(req);
    }
}
