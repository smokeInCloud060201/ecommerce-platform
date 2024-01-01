package com.karson.ecommerce.crmapi.repositories;

import com.karson.ecommerce.common.repository.BaseRepository;
import com.karson.ecommerce.crmapi.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    Optional<Permission> findByNameIgnoreCase(String name);

    @Query(value = "SELECT * FROM roles JOIN ", nativeQuery = true)
    List<Permission> getAllByRole(String roleName);
}
