package com.karson.ecommerce.common.controller;

import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.AccessDeniedException;
import com.karson.ecommerce.common.exceptions.BadRequestException;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.karson.ecommerce.common.constants.ResponseErrorCode.BAD_REQUEST;
import static com.karson.ecommerce.common.constants.ResponseErrorCode.RESOURCE_NOT_FOUND;
import static com.karson.ecommerce.common.constants.ResponseErrorCode.UNKNOW_ERROR;
import static com.karson.ecommerce.common.constants.ResponseErrorCode.UN_AUTHORIZE;

@RestController
public class BaseController {

    private static final String V1 = "/v1";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseDto<Object> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        final String message = accessDeniedException.getMessage();
        return ResponseDto.builder()
                .errorCode(UN_AUTHORIZE)
                .errorMessage(message)
                .statusCode(401)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<Object> handleBadRequestException(BadRequestException badRequestException) {
        final String message = badRequestException.getMessage();
        return ResponseDto.builder()
                .errorCode(BAD_REQUEST)
                .errorMessage(message)
                .statusCode(400)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        final String message = resourceNotFoundException.getMessage();
        return ResponseDto.builder()
                .errorCode(RESOURCE_NOT_FOUND)
                .statusCode(404)
                .errorMessage(message)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto<Object> handleGlobalException (Exception exception) {
        final String message = exception.getMessage();
        return ResponseDto.builder()
                .errorCode(UNKNOW_ERROR)
                .errorMessage(message)
                .statusCode(500)
                .build();
    }
}
