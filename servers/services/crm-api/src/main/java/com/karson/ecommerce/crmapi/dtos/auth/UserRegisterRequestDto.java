package com.karson.ecommerce.crmapi.dtos.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterRequestDto {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    private boolean isEnabled = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isAccountNonExpired = true;

}
