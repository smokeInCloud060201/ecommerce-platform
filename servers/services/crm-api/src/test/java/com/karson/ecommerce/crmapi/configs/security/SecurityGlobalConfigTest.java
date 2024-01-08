package com.karson.ecommerce.crmapi.configs.security;

import com.karson.ecommerce.crmapi.entity.Role;
import com.karson.ecommerce.crmapi.entity.User;
import com.karson.ecommerce.crmapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SecurityGlobalConfigTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityGlobalConfig securityGlobalConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userDetailsServiceValidUsernameReturnsUserDetails() {

        User user = new User();
        user.setEmail("testUser");
        user.setPassword("password");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role());
        user.setRoles(roles);
        when(userRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = securityGlobalConfig.userDetailsService().loadUserByUsername("testUser");

        assertNotNull(userDetails);
    }

    @Test
    void userDetailsServiceInvalidUsername_hrowsException() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> securityGlobalConfig.userDetailsService().loadUserByUsername("nonExistentUser"));
    }

    @Test
    void authenticationProviderReturnsDaoAuthenticationProvider() {
        assertNotNull(securityGlobalConfig.authenticationProvider());
        assertInstanceOf(DaoAuthenticationProvider.class, securityGlobalConfig.authenticationProvider());
    }


    @Test
    void passwordEncoderReturnsBCryptPasswordEncoder() {
        assertNotNull(securityGlobalConfig.passwordEncoder());
        assertInstanceOf(BCryptPasswordEncoder.class, securityGlobalConfig.passwordEncoder());
    }
}
