package com.karson.ecommerce.crmapi.dtos.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionDto {
    private String id;

    @JsonProperty("permission_name")
    private String name;
}
