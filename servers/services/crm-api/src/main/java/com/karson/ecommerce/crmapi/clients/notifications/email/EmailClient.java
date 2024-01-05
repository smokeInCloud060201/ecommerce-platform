package com.karson.ecommerce.crmapi.clients.notifications.email;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "email",
        url = "http://localhost:8088"
)
public interface EmailClient {
    @PostMapping("/api/v1/mail/send")
     String sendEmail(@RequestParam String mailAddress, @RequestBody EmailDto emailDto);
}
