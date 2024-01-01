package com.karson.ecommerce.crmapi.mapper;

import com.karson.ecommerce.crmapi.dtos.permission.PermissionDto;
import com.karson.ecommerce.crmapi.dtos.permission.RoleDto;
import com.karson.ecommerce.crmapi.entity.Permission;
import com.karson.ecommerce.crmapi.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    PermissionMapper permissionMapper = Mappers.getMapper(PermissionMapper.class);

    @Mapping(source = "permissionsRole", target = "permissionDto", qualifiedByName = "PermissionToDto")
    RoleDto mapToDto(Role role);

    @Mapping(source = "permissionDto", target = "permissionsRole", qualifiedByName = "PermissionDtoToEntity")
    Role mapToEntity(RoleDto roleDto);

    @Named("PermissionDtoToEntity")
    static Set<Permission> permissionsDtoToEntity(Set<PermissionDto> permissionDtos) {
        return permissionDtos.stream().map(permissionMapper::mapToEntity).collect(Collectors.toSet());
    }

    @Named("PermissionToDto")
    static Set<PermissionDto> permissionsToDto(Set<Permission> permissions) {
        return permissions.stream().map(permissionMapper::mapToDto).collect(Collectors.toSet());
    }
}
