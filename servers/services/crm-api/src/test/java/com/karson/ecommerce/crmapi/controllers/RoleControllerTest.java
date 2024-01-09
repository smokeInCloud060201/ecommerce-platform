package com.karson.ecommerce.crmapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karson.ecommerce.crmapi.controller.RoleController;
import com.karson.ecommerce.crmapi.dtos.permission.RoleDto;
import com.karson.ecommerce.crmapi.services.RoleService;
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

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testUpsertRole() throws Exception {
        RoleDto requestDto = new RoleDto();
        // Set up requestDto as needed

        when(roleService.upsertRole(requestDto)).thenReturn(new RoleDto()); // Adjust the response as needed

        performPostRequest("/api/v1/roles/upsert", requestDto, status().isOk());
    }

    @Test
    public void testDeleteRole() throws Exception {
        long id = 1L;

        doNothing().when(roleService).deleteRole(id);

        performDeleteRequest("/api/v1/roles/" + id, status().isOk());
    }

    @Test
    public void testGetRoles() throws Exception {
        when(roleService.getRoles(null)).thenReturn(Collections.singletonList(new RoleDto())); // Adjust the response as needed

        performGetRequest("/api/v1/roles", status().isOk());
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

    private void performDeleteRequest(String url, ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(resultMatcher);
    }

    private void performGetRequest(String url, ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url)
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
