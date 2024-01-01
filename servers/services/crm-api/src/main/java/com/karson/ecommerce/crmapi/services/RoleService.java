package com.karson.ecommerce.crmapi.services;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.dtos.permission.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto upsertRole(RoleDto roleDto);
    void deleteRole(Long id) throws ResourceNotFoundException;
    List<RoleDto> getRoles(SearchDto searchDto);
}
