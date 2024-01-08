package com.karson.ecommerce.crmapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karson.ecommerce.crmapi.configs.security.JwtService;
import com.karson.ecommerce.crmapi.controller.AuthenticationController;
import com.karson.ecommerce.crmapi.dtos.TokenDto;
import com.karson.ecommerce.crmapi.dtos.auth.LoginRequestDto;
import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;
import com.karson.ecommerce.crmapi.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Configuration
    static class TestConfig {
        @Bean
        public JwtService jwtService() {
            return Mockito.mock(JwtService.class);
        }
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        // Set up requestDto as needed

        when(userService.upsertUser(requestDto)).thenReturn(new UserResponseDto()); // Adjust the response as needed

        performPostRequest("/api/v1/auth/register", requestDto, status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto();
        // Set up requestDto as needed

        when(userService.login(requestDto)).thenReturn(new TokenDto()); // Adjust the response as needed

        performPostRequest("/api/v1/auth/login", requestDto, status().isOk());
    }

    @Test
    public void testVerifyOtp() throws Exception {
        String email = "test@example.com";
        String code = "123456";

        doNothing().when(userService).verifyOTP(email, code);

        performPostRequest("/api/v1/auth/verify-otp?email=" + email + "&code=" + code, null, status().isOk());
    }

    private void performPostRequest(String url, Object requestDto, ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(resultMatcher);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
