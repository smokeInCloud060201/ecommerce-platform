package com.karson.ecommerce.pemapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.karson.ecommerce.common",
                "com.karson.ecommerce.pemapi",
        })
public class PemApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PemApiApplication.class, args);
    }

}
