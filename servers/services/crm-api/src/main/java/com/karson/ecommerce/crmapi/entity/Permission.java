package com.karson.ecommerce.crmapi.entity;

import com.karson.ecommerce.common.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
public class Permission extends BaseEntity {
    @Column
    String name;

    @ManyToMany(mappedBy = "permissionsRole", fetch = FetchType.LAZY)
    Set<Role> roles = new HashSet<>();
}
