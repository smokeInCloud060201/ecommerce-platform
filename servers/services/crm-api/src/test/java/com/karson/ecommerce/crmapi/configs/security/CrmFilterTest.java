package com.karson.ecommerce.crmapi.configs.security;

import com.karson.ecommerce.common.configs.sercurity.context.AuthModel;
import com.karson.ecommerce.common.configs.sercurity.context.ContextModel;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CrmFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private CrmFilter crmFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void doFilterInternalValidTokenContextSetInSecurityContextHolder() throws ServletException, IOException {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String validToken = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(jwtService.extractUsername(validToken)).thenReturn("username");

        UserDetails userDetails = mock(UserDetails.class);
        AuthModel authModel = mock(AuthModel.class);
        when(jwtService.extractContextModel(validToken)).thenReturn(authModel);
        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtService.isTokenValid(validToken, userDetails)).thenReturn(true);

        // Act
        crmFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).extractContextModel(validToken);
        verify(userDetailsService).loadUserByUsername("username");
        verify(jwtService).isTokenValid(validToken, userDetails);

        // Add additional assertions as needed to verify that the security context is set correctly.
    }
}
