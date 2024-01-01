package com.karson.ecommerce.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;

    @Builder.Default
    private LocalDateTime expiredTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis() + 86400000),
            ZoneId.systemDefault()
    );
}
