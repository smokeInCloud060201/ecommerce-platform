package com.karson.ecommerce.paymentapi.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    INITIAL,
    SUCCESS,
    COMPLETED,
    PENDING,
    REFUNDED,
    CANCELLED,
    UNKNOWN
}
