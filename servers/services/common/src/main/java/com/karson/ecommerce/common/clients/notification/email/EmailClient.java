package com.karson.ecommerce.common.clients.notification.email;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "email",
        url = "http://localhost:8088",
        path = "/api/v1/mail"
)
public interface EmailClient {
    @PostMapping("/send/{mailAddress}")
     String sendEmail(@PathVariable String mailAddress, @RequestBody EmailDto emailDto);
}
