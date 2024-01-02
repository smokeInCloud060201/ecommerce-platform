package com.karson.ecommerce.crmapi.controller;

import com.karson.ecommerce.common.controller.BaseController;
import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.common.utils.DtoUtil;
import com.karson.ecommerce.crmapi.dtos.permission.RoleDto;
import com.karson.ecommerce.crmapi.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseController {

    private final RoleService roleService;

    @PostMapping("/upsert")
    public ResponseDto<RoleDto> upsertRole(@RequestBody RoleDto roleDto) {
        return DtoUtil.toResponseDto(roleService.upsertRole(roleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Object> deleteRole(@PathVariable("id") Long id) throws ResourceNotFoundException {
         roleService.deleteRole(id);
        return DtoUtil.toResponseDto("Successfully");
    }

    @GetMapping
    public ResponseDto<List<RoleDto>> getRoles() {
        return DtoUtil.toResponseDto(roleService.getRoles(null));
    }
}
