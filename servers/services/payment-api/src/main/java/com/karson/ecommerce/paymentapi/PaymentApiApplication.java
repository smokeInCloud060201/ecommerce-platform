package com.karson.ecommerce.paymentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
   scanBasePackages = {
      "com.karson.ecommerce.common",
      "com.karson.ecommerce.paymentapi",
})

public class PaymentApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(PaymentApiApplication.class, args);
    }

}
