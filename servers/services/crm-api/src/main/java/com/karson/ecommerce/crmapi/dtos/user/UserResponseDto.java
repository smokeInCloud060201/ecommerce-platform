package com.karson.ecommerce.crmapi.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("is_verified")
    private boolean isVerified;

    @JsonProperty("is_account_non_expired")
    private boolean isAccountNonExpired;

    @JsonProperty("is_enabled")
    private boolean isEnabled;

    @JsonProperty("is_credentials_non_expired")
    private boolean isCredentialsNonExpired;

    @JsonProperty("is_account_non_locked")
    private boolean isAccountNonLocked;
}
