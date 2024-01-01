package com.karson.ecommerce.crmapi.mapper;

import com.karson.ecommerce.crmapi.dtos.permission.PermissionDto;
import com.karson.ecommerce.crmapi.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    @Mapping(source = "name", target = "name", qualifiedByName = "MapNameDtoToNameEntity")
    PermissionDto mapToDto(Permission permission);

    @Mapping(source = "name", target = "name", qualifiedByName = "MapNameDtoToNameEntity")
    Permission mapToEntity(PermissionDto permissionDto);

    @Named("MapNameDtoToNameEntity")
    static String mapName(String name) {
        return name;
    }
}
