package com.karson.ecommerce.notification.repositories;

import com.karson.ecommerce.common.repository.BaseRepository;
import com.karson.ecommerce.notification.entities.EmailTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends BaseRepository<EmailTemplate, Long> {
    @Query(value = "SELECT * FROM email_template WHERE type = :type", nativeQuery = true)
    Optional<EmailTemplate> findByEmailType(@Param("type") String type);
}
