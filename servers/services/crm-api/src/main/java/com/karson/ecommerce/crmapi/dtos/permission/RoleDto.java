package com.karson.ecommerce.crmapi.dtos.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {

    private String id;

    private String name;

    @JsonProperty("permissions")
    private Set<PermissionDto> permissionDto;
}
