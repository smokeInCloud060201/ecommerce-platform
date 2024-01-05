package com.karson.ecommerce.notification.entities;

import com.karson.ecommerce.common.entities.BaseEntity;
import com.karson.ecommerce.notification.enums.NotificationEmailType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email_tamplate")
public class EmailTemplate extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationEmailType emailType;
    private String subject;
    private String message;
}
