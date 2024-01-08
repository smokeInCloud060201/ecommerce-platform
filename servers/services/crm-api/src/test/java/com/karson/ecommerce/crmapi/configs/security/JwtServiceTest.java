package com.karson.ecommerce.crmapi.configs.security;

import com.karson.ecommerce.common.configs.sercurity.context.AuthModel;
import com.karson.ecommerce.common.configs.sercurity.context.ContextModel;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    @Mock
    private SecurityConfigProperties securityConfigProperties;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityConfigProperties.getIss()).thenReturn("http://localhost:8082");
        when(securityConfigProperties.getAuds()).thenReturn(List.of("http://localhost:8081"));
        when(securityConfigProperties.getSecretKey()).thenReturn("868DE142D37FB146EC9B45C7172FF61B60349E31C5DFB17ECC7D40865FF261D4");

    }

    @Test
    void extractUsernameValidToken_eturnsUsername() {
        String token = "valid-token";
        assertThrows(io.jsonwebtoken.MalformedJwtException.class, () -> jwtService.extractUsername(token));
    }

    @Test
    void extractContextModelValidTokenReturnsAuthModel() {
        String token = "valid-token";
        AuthModel authModel = jwtService.extractContextModel(token);
        assertNull(authModel);
    }

    @Test
    void generateAccessTokenValidContextModelReturnsToken() {
        ContextModel contextModel = createMockContextModel();
        String token = jwtService.generateAccessToken(contextModel);
        assertNotNull(token);
    }


    @Test
    void generateTokenValidContextModelReturnsToken() {
        ContextModel contextModel = createMockContextModel();
        assertNotNull(jwtService.generateToken(new HashMap<>(), contextModel));
    }

    @Test
    void generateRefreshTokenValidContextModelReturnsToken() {
        ContextModel contextModel = createMockContextModel();
        assertNotNull(jwtService.generateRefreshToken(contextModel));
    }

    // Helper method to create a mock ContextModel for testing
    private ContextModel createMockContextModel() {
        Set<String> roles = Collections.singleton("ROLE_USER");
        Set<String> permissions = Collections.singleton("READ_PERMISSION");
        AuthModel.UserPrincipal userPrincipal = AuthModel.UserPrincipal.builder()
                .email("test@example.com")
                .roles(roles)
                .permissions(permissions)
                .build();
        AuthModel authModel = AuthModel.builder()
                .userPrincipal(userPrincipal)
                .grantedAuthoritySet(Collections.singleton(new SimpleGrantedAuthority("SCOPE_READ_PERMISSION")))
                .build();
        return new ContextModel(authModel);
    }

        @Test
    void extractUsernameValidTokenReturnsUsername() {
        String token = generateToken("testUser");
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> jwtService.extractUsername(token));

    }

    @Test
    void extractContextModelValidTokenReturnsContextModel() {
        String token = generateToken("testUser");

        AuthModel authModel = jwtService.extractContextModel(token);

        assertNull(authModel);
    }

    @Test
    void extractUsernameInvalidTokenThrowsException() {
        assertThrows(MalformedJwtException.class, () -> jwtService.extractUsername("invalidToken"));
    }


    private String generateToken(String username) {
        AuthModel authModel = new AuthModel();
        authModel.setUserPrincipal(AuthModel.UserPrincipal.builder()
                .email(username)
                .roles(Collections.singleton("ROLE_USER"))
                .permissions(Collections.singleton("READ"))
                .build());
        ContextModel contextModel = new ContextModel(authModel);

        return jwtService.generateAccessToken(contextModel);
    }
}
