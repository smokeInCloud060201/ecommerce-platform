package com.karson.ecommerce.crmapi.controller;

import com.karson.ecommerce.common.controller.BaseController;
import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.common.utils.DtoUtil;
import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;
import com.karson.ecommerce.crmapi.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController extends BaseController {

    private final UserServiceImpl userService;

    @GetMapping("/{userName}")
    public ResponseDto<UserResponseDto> findUserByName(@PathVariable("userName") String userName) throws ResourceNotFoundException {
        return DtoUtil.toResponseDto((userService.findByUserName(userName)));

    }

    @GetMapping("/get-user-by-token")
    public ResponseDto<UserResponseDto> loadUserByToken() {
        return DtoUtil.toResponseDto((userService.loadUserByToken()));
    }

    @PutMapping
    public ResponseDto<UserResponseDto> updateUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        return DtoUtil.toResponseDto(userService.upsertUser(userRegisterRequestDto));

    }

    @PutMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
    }

    @PutMapping("/roles/{id}")
    public ResponseDto<UserResponseDto> updateRole(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return DtoUtil.toResponseDto(userService.addRoleToUser(id));
    }
}
