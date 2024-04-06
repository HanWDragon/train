package com.han.train.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Han
 * @date 2023年09月05日 08:57
 */
@RestController
@RefreshScope
public class TestController {

    @Value("${test.nacos}")
    private String test;


    @GetMapping("/hello")
    public String hello() {
        return String.format("Hello  %s!", test);
    }

}
