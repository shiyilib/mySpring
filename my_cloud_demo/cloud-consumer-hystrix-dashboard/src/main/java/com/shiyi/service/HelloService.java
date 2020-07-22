package com.shiyi.service;

import com.shiyi.service.impl.HelloServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer",fallback = HelloServiceHystrix.class)
public interface HelloService {

    @RequestMapping("/hello")
    String hello(@RequestParam(value = "name") String name);
}
