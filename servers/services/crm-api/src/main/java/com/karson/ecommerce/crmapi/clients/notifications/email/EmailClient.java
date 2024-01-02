package com.karson.ecommerce.crmapi.clients.notifications.email;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "email",
        url = "http://localhost:8088"
)
public interface EmailClient {
    @PostMapping("/api/v1/mail/send/{mailAddress}")
     String sendEmail(@PathVariable String mailAddress, @RequestBody EmailDto emailDto);
}
