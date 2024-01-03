package com.karson.ecommerce.crmapi.controller;

import com.karson.ecommerce.common.controller.BaseController;
import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.dtos.TokenDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.common.utils.DtoUtil;
import com.karson.ecommerce.crmapi.dtos.auth.LoginRequestDto;
import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;
import com.karson.ecommerce.crmapi.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController extends BaseController {

    private final UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseDto<UserResponseDto> createUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        return DtoUtil.toResponseDto(userService.upsertUser(userRegisterRequestDto));
    }

    @PostMapping("/login")
    public ResponseDto<TokenDto> login(@RequestBody LoginRequestDto loginRequest) throws ResourceNotFoundException {
        return DtoUtil.toResponseDto(userService.login(loginRequest));
    }
}
