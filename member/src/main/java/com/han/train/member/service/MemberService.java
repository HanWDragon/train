package com.han.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.han.train.common.exception.BusinessException;
import com.han.train.common.exception.BusinessExceptionEnum;
import com.han.train.common.util.SnowUtil;
import com.han.train.member.domain.Member;
import com.han.train.member.domain.MemberExample;
import com.han.train.member.mapper.MemberMapper;
import com.han.train.member.requset.MemberLoginReq;
import com.han.train.member.requset.MemberRegisterReq;
import com.han.train.member.requset.MemberSendCodeReq;
import com.han.train.member.response.MemberLoginResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Han
 * @date 2023年09月05日 20:14
 */

@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;

    public int count() {
        return Math.toIntExact(memberMapper.countByExample(null));
    }


    public long register(MemberRegisterReq req) {
        String mobile = req.getMobile();
        Member memberDB = selectByMobile(mobile);
        // 带验证码的注册可以用这种方式，有验证码，说明手机号是本人用，原来注册过的，就不需要保存了，直接登录
        // 也就是这个接口块，既能注册又能登录
        if (ObjectUtil.isNotNull(memberDB)) {
            // 这是一种做法，还有一种做法是抛出异常中断业务
//            return members.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        Member memberDB = selectByMobile(mobile);
        // 如果手机号不存在则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        } else {
            LOG.info("手机号存在，不插入记录");
        }

        // 生成验证码
//        String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成短信验证码：{}", code);
        // TODO 保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间
        LOG.info("保存短信记录表");
        // TODO 对接短信通道，发送短信
        LOG.info("对接短信通道");
    }

    public MemberLoginResp login(MemberLoginReq req) {
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = selectByMobile(mobile);

        // 如果手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码
        if (!"8888".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        //        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
//        memberLoginResp.setToken(token);
        return BeanUtil.copyProperties(memberDB, MemberLoginResp.class);

    }

    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(members)) {
            return null;
        } else {
            return members.get(0);
        }
    }

}
