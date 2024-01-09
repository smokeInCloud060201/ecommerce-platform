package com.karson.ecommerce.crmapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karson.ecommerce.crmapi.controller.UserController;
import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;
import com.karson.ecommerce.crmapi.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testFindUserByName() throws Exception {
        String userName = "testUser";

        when(userService.findByUserName(userName)).thenReturn(new UserResponseDto()); // Adjust the response as needed

        performGetRequest("/api/v1/users/" + userName, status().isOk());
    }

    @Test
    public void testLoadUserByToken() throws Exception {
        when(userService.loadUserByToken()).thenReturn(new UserResponseDto()); // Adjust the response as needed

        performGetRequest("/api/v1/users/get-user-by-token", status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        // Set up requestDto as needed

        when(userService.upsertUser(requestDto)).thenReturn(new UserResponseDto()); // Adjust the response as needed

        performPutRequest("/api/v1/users", requestDto, status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        long id = 1L;

        doNothing().when(userService).deleteUser(id);

        performPutRequest("/api/v1/users/" + id, null, status().isOk());
    }

    @Test
    public void testUpdateRole() throws Exception {
        long id = 1L;

        when(userService.addRoleToUser(id)).thenReturn(new UserResponseDto()); // Adjust the response as needed

        performPutRequest("/api/v1/users/roles/" + id, null, status().isOk());
    }

    private void performGetRequest(String url, ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(resultMatcher);
    }

    private void performPutRequest(String url, Object requestDto, ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
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
