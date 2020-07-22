package com.shiyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShiyiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiyiApplication.class, args);
    }

}
