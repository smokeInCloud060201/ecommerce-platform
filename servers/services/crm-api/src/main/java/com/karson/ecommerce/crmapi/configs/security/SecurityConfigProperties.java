package com.karson.ecommerce.crmapi.configs.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "security.configs", ignoreInvalidFields = true)
public class SecurityConfigProperties {
    private String secretKey;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
    private String iss;
    private List<String> auds;
}
