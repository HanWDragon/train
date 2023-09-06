package com.han.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.han.train.member.domain.Member;
import com.han.train.member.domain.MemberExample;
import com.han.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Han
 * @date 2023年09月05日 20:14
 */

@Service
public class MemberService {

    @Resource
    private MemberMapper memberMapper;

    public int count() {
        return Math.toIntExact(memberMapper.countByExample(null));
    }


    public long register(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);
        // 带验证码的注册可以用这种方式，有验证码，说明手机号是本人用，原来注册过的，就不需要保存了，直接登录
        // 也就是这个接口块，既能注册又能登录
        if (CollUtil.isNotEmpty(members)) {
            // 这是一种做法，还有一种做法是抛出异常中断业务
//            return members.get(0).getId();
            throw new RuntimeException("手机号已注册");
        }


        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

}
