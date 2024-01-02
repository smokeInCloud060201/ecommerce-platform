package com.karson.ecommerce.crmapi.services.impl;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.dtos.permission.RoleDto;
import com.karson.ecommerce.crmapi.entity.Role;
import com.karson.ecommerce.crmapi.mapper.RoleMapper;
import com.karson.ecommerce.crmapi.repositories.RoleRepository;
import com.karson.ecommerce.crmapi.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto upsertRole(RoleDto roleDto) {
        Role role = roleMapper.mapToEntity(roleDto);
        role = roleRepository.save(role);
        return roleMapper.mapToDto(role);
    }

    @Override
    public void deleteRole(Long id) throws ResourceNotFoundException {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not exists"));
        roleRepository.delete(role);
    }

    @Override
    public List<RoleDto> getRoles(SearchDto searchDto) {
        return roleRepository.findAll().stream().map(roleMapper::mapToDto).toList();
    }
}
