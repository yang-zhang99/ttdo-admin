package com.me.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication

@EnableDiscoveryClient
public class TtdoIamApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtdoIamApplication.class, args);
    }

}