package com.karson.ecommerce.paymentapi.entities;

import com.karson.ecommerce.common.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "payment_data")
public class PaymentData extends BaseEntity {
    @Column
    private String name;

    @Column
    private String value;
}
