package com.karson.ecommerce.common.configs.sercurity.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthModel {

    private UserPrincipal userPrincipal;
    @Builder.Default
    private Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPrincipal {
        private String email;
        @Builder.Default
        private Set<String> roles = new HashSet<>();
        @Builder.Default
        private Set<String> permissions = new HashSet<>();
        private boolean isVerified;
        private boolean isEnabled;
        private boolean isAccountNonExpired;
        private boolean isAccountNonLocked;
        private boolean isCredentialsNonExpired;
    }
}
