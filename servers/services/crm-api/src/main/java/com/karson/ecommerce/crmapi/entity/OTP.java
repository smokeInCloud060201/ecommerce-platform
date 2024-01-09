package com.karson.ecommerce.crmapi.entity;

import com.karson.ecommerce.common.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "otp")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OTP extends BaseEntity {

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userOtp;
}
