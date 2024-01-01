package com.karson.ecommerce.common.constants;

import lombok.Getter;

@Getter
public class SecurityConstant {
    private SecurityConstant() {}

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer";
}
