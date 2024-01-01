package com.karson.ecommerce.paymentapi.entities;

import com.karson.ecommerce.common.entities.BaseEntity;
import com.karson.ecommerce.paymentapi.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Column
    private BigDecimal amount;

    @Column
    private Double taxAmount;

    @Column
    private Double tax;

    @Column
    private String emailAddress;

    @Column
    private String portalCode;

    @Column
    private String cardCvv;

    @Column
    private String cardNumber;

    @Column
    private String cardExpired;

    @Column
    private String cardAlias;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany
    private List<PaymentData> paymentData;
}
