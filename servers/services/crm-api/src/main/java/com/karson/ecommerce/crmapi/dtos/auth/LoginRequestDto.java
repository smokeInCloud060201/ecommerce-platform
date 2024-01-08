package com.karson.ecommerce.crmapi.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LoginRequestDto {

    private String userName;
    private String password;
}
