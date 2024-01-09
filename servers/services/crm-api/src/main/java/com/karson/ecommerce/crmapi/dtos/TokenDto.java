package com.karson.ecommerce.crmapi.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;

    @Setter(AccessLevel.NONE)
    private LocalDateTime expiredTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis() + 86400000),
            ZoneId.systemDefault()
    );
}
