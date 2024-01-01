package com.karson.ecommerce.gateway.configs;

import com.karson.ecommerce.gateway.security.WhitelistEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class RouterValidate {
    private WhitelistEndpoint whitelistEndpoint ;

    public RouterValidate(WhitelistEndpoint whitelistEndpoint) {
        this.whitelistEndpoint = whitelistEndpoint;
    }

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> !whitelistEndpoint.getWhitelists().isEmpty() && whitelistEndpoint.getWhitelists().stream()
                    .anyMatch(endpoint -> serverHttpRequest.getURI().getPath().contains(endpoint));
}
