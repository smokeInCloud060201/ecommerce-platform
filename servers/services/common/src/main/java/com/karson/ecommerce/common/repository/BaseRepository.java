package com.karson.ecommerce.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface BaseRepository<T, U> extends JpaRepository<T, U> {

    @Query(value = "DELETE FROM :tableName WHERE is_delete = TRUE ", nativeQuery = true)
    void safeDelete(@Param("tableName") String table);
}
