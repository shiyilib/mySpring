package com.shiyi.service.impl;

import com.shiyi.service.HelloService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloServiceHystrix implements HelloService {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello" + name + "message send failed";
    }
}
