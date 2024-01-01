package com.karson.ecommerce.crmapi.services.impl;

import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.dtos.permission.PermissionDto;
import com.karson.ecommerce.crmapi.entity.Permission;
import com.karson.ecommerce.crmapi.mapper.PermissionMapper;
import com.karson.ecommerce.crmapi.repositories.PermissionRepository;
import com.karson.ecommerce.crmapi.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionDto upsertPermission(PermissionDto permissionDto) {
        Permission permission = permissionRepository.findByNameIgnoreCase(permissionDto.getName()).orElse(new Permission());
        permission.setName(permissionDto.getName());
        permission = permissionRepository.save(permission);
        return permissionMapper.mapToDto(permission);
    }

    @Override
    public void deletePermission(Long id) throws ResourceNotFoundException {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not exist permission"));
        permission.setDeleted(true);
        permissionRepository.save(permission);
    }

    @Override
    public List<PermissionDto> getPermissions() {
        return permissionRepository.findAll().stream().map(permissionMapper::mapToDto).toList();
    }

}
