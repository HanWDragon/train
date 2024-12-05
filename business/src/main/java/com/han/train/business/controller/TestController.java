package com.han.train.business.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Han
 * @date 2023年09月05日 08:57
 */
@RestController
public class TestController {
    @SentinelResource(value = "hello")
    @GetMapping("/hello")
    public String hello() {
//        Thread.sleep(500);
        return "Hello World Business";
    }

}
