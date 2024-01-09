package com.karson.ecommerce.crmapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karson.ecommerce.crmapi.controller.PermissionController;
import com.karson.ecommerce.crmapi.dtos.permission.PermissionDto;
import com.karson.ecommerce.crmapi.services.PermissionService;
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

class PermissionsControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private PermissionController permissionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
    }

    @Test
    public void testUpsertPermission() throws Exception {
        PermissionDto requestDto = new PermissionDto();
        // Set up requestDto as needed

        when(permissionService.upsertPermission(requestDto)).thenReturn(new PermissionDto()); // Adjust the response as needed

        performPostRequest("/api/v1/permissions/upsert", requestDto, status().isOk());
    }

    @Test
    public void testDeletePermission() throws Exception {
        long id = 1L;

        doNothing().when(permissionService).deletePermission(id);

        performDeleteRequest("/api/v1/permissions/" + id, status().isOk());
    }

    @Test
    public void testGetPermissions() throws Exception {
        when(permissionService.getPermissions()).thenReturn(Collections.singletonList(new PermissionDto())); // Adjust the response as needed

        performGetRequest("/api/v1/permissions", status().isOk());
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
