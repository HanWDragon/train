package com.han.train.batch.controller;

import com.han.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Han
 * @date 2023年09月05日 08:57
 */
@RestController
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    BusinessFeign businessFeign;

    @GetMapping("/hello")
    public String hello() {
        String businessHello = businessFeign.hello();
        LOG.info(businessHello);
        return "Hello World! Batch! " + businessHello;
    }

}
