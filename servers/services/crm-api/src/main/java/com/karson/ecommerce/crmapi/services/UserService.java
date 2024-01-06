package com.karson.ecommerce.crmapi.services;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.BadRequestException;
import com.karson.ecommerce.crmapi.dtos.TokenDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.dtos.auth.LoginRequestDto;
import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto upsertUser(UserRegisterRequestDto userRegisterRequestDto);

    UserResponseDto findByUserName(String userName) throws ResourceNotFoundException;
    UserResponseDto loadUserByToken() throws ResourceNotFoundException;

    void deleteUser(Long userId) throws ResourceNotFoundException;

    UserResponseDto findByUserId(Long userId) throws ResourceNotFoundException;

    List<UserResponseDto> searchUser(SearchDto searchDto);

    TokenDto login(LoginRequestDto loginRequest) throws ResourceNotFoundException;

    UserResponseDto addRoleToUser(Long roleId) throws ResourceNotFoundException;

    void verifyOTP(String emailAddress, String otpCode) throws BadRequestException, ResourceNotFoundException;

}
