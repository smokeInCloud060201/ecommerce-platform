package com.karson.ecommerce.gateway.security;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security")
@Getter
public class WhitelistEndpoint {
    private List<String> whitelists = new ArrayList<>();
}
