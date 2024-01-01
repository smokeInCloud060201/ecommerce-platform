package com.karson.ecommerce.crmapi.repositories;

import com.karson.ecommerce.common.repository.BaseRepository;
import com.karson.ecommerce.crmapi.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    @Query(value = "SELECT * FROM roles where name = :name", nativeQuery = true)
    Optional<Role> findByName(@Param("name") String name);
}
