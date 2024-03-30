package com.han.train.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Han
 * @date 2023年09月05日 08:57
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World Business";
    }

}
