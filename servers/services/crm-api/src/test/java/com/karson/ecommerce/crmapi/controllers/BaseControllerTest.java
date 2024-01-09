package com.karson.ecommerce.crmapi.controllers;

import com.karson.ecommerce.common.controller.BaseController;
import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.AccessDeniedException;
import com.karson.ecommerce.common.exceptions.BadRequestException;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.karson.ecommerce.common.constants.ResponseErrorCode.BAD_REQUEST;
import static com.karson.ecommerce.common.constants.ResponseErrorCode.RESOURCE_NOT_FOUND;
import static com.karson.ecommerce.common.constants.ResponseErrorCode.UNKNOW_ERROR;
import static com.karson.ecommerce.common.constants.ResponseErrorCode.UN_AUTHORIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BaseControllerTest {
    @Mock
    private AccessDeniedException accessDeniedException;

    @Mock
    private BadRequestException badRequestException;

    @Mock
    private ResourceNotFoundException resourceNotFoundException;

    @Mock
    private Exception globalException;

    @InjectMocks
    private BaseController baseController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleAccessDeniedException_ReturnsCorrectResponseDto() {
        // Arrange
        when(accessDeniedException.getMessage()).thenReturn("Access Denied");

        // Act
        ResponseDto<Object> responseDto = baseController.handleAccessDeniedException(accessDeniedException);

        // Assert
        assertEquals(UN_AUTHORIZE, responseDto.getErrorCode());
        assertEquals("Access Denied", responseDto.getErrorMessage());
        assertEquals(401, responseDto.getStatusCode());
    }

    @Test
    void handleBadRequestException_ReturnsCorrectResponseDto() {
        // Arrange
        when(badRequestException.getMessage()).thenReturn("Bad Request");

        // Act
        ResponseDto<Object> responseDto = baseController.handleBadRequestException(badRequestException);

        // Assert
        assertEquals(BAD_REQUEST, responseDto.getErrorCode());
        assertEquals("Bad Request", responseDto.getErrorMessage());
        assertEquals(400, responseDto.getStatusCode());
    }

    @Test
    void handleResourceNotFoundException_ReturnsCorrectResponseDto() {
        // Arrange
        when(resourceNotFoundException.getMessage()).thenReturn("Resource Not Found");

        // Act
        ResponseDto<Object> responseDto = baseController.handleResourceNotFoundException(resourceNotFoundException);

        // Assert
        assertEquals(RESOURCE_NOT_FOUND, responseDto.getErrorCode());
        assertEquals("Resource Not Found", responseDto.getErrorMessage());
        assertEquals(404, responseDto.getStatusCode());
    }

    @Test
    void handleGlobalException_ReturnsCorrectResponseDto() {
        // Arrange
        when(globalException.getMessage()).thenReturn("Unknown Error");

        // Act
        ResponseDto<Object> responseDto = baseController.handleGlobalException(globalException);

        // Assert
        assertEquals(UNKNOW_ERROR, responseDto.getErrorCode());
        assertEquals("Unknown Error", responseDto.getErrorMessage());
        assertEquals(500, responseDto.getStatusCode());
    }
}
