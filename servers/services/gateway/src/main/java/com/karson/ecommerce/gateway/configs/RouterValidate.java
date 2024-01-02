package com.karson.ecommerce.gateway.configs;

import com.karson.ecommerce.gateway.security.WhitelistEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouterValidate {
    private final WhitelistEndpoint whitelistEndpoint;

    public boolean isSecured(ServerHttpRequest serverHttpRequest) {
        return !whitelistEndpoint.getWhitelists().isEmpty() &&
                whitelistEndpoint.getWhitelists().stream()
                        .anyMatch(endpoint -> serverHttpRequest.getURI().getPath().contains(endpoint));
    }

}