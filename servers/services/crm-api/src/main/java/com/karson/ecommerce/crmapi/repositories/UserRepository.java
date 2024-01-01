package com.karson.ecommerce.crmapi.repositories;

import com.karson.ecommerce.common.repository.BaseRepository;
import com.karson.ecommerce.crmapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users where users.email = :userName", nativeQuery = true)
    Optional<User> findByUsername(@PathVariable("userName") String userName);
}
