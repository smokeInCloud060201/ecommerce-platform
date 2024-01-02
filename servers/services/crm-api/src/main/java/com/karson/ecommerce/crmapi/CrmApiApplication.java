package com.karson.ecommerce.crmapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "com.karson.ecommerce.common",
                "com.karson.ecommerce.crmapi",
        })
@EnableDiscoveryClient
@EnableFeignClients
public class CrmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApiApplication.class, args);
    }

}
