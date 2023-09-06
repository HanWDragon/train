package com.han.train.member.controller;

import com.han.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Han
 * @date 2023年09月05日 08:57
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public int count() {
        return memberService.count();
    }

    @PostMapping("/register")
    public long register(String mobile) {
        return memberService.register(mobile);
    }

}
