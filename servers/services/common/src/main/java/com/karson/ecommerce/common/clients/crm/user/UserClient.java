package com.karson.ecommerce.common.clients.crm.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "user",
        url = "http://localhost:8082",
        path = "/api/v1/users"
)
public interface UserClient {
    @GetMapping("/get-user-by-token")
    String getUserId();
}
