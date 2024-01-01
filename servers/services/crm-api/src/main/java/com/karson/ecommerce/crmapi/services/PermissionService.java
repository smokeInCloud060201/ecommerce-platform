package com.karson.ecommerce.crmapi.services;

import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.dtos.permission.PermissionDto;

import java.util.List;

public interface PermissionService {

    PermissionDto upsertPermission(PermissionDto permissionDto);
    void deletePermission(Long id) throws ResourceNotFoundException;
    List<PermissionDto> getPermissions();

}
