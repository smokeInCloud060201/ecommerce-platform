package com.karson.ecommerce.crmapi.repositories;

import com.karson.ecommerce.common.repository.BaseRepository;
import com.karson.ecommerce.crmapi.entity.OTP;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends BaseRepository<OTP, Long> {

    @Query(value = "SELECT o.* FROM otp o " +
            "LEFT JOIN users u ON o.user_id = u.id " +
            "WHERE o.code = :code AND u.is_deleted = false AND o.is_deleted = false AND u.email = :email", nativeQuery = true)
    Optional<OTP> findOtpByCode(@Param("code") String code, @Param("email") String email);
}
