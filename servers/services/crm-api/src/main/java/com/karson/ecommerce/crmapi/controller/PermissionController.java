package com.karson.ecommerce.crmapi.controller;

import com.karson.ecommerce.common.controller.BaseController;
import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.common.utils.DtoUtil;
import com.karson.ecommerce.crmapi.dtos.permission.PermissionDto;
import com.karson.ecommerce.crmapi.services.PermissionService;
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
@RequestMapping("/api/v1/permissions")
public class PermissionController extends BaseController {
    private final PermissionService permissionService;

    @PostMapping("/upsert")
    public ResponseDto<PermissionDto> upsertPermission(@RequestBody PermissionDto permissionDto) {
        return DtoUtil.toResponseDto(permissionService.upsertPermission(permissionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Object> deletePermission(@PathVariable("id") Long id) throws ResourceNotFoundException {
        permissionService.deletePermission(id);
        return DtoUtil.toResponseDto("Successfully");
    }

    @GetMapping
    public ResponseDto<List<PermissionDto>> getPermission() {
        return DtoUtil.toResponseDto(permissionService.getPermissions());
    }
}
