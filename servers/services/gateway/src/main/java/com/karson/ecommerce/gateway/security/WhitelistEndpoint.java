package com.karson.ecommerce.gateway.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security")
@Getter
@Setter
@RequiredArgsConstructor
public class WhitelistEndpoint {
    private final List<String> whitelists = new ArrayList<>();
}
