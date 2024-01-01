package com.karson.ecommerce.paymentapi.controllers;

import lombok.RequiredArgsConstructor;
import com.karson.ecommerce.common.controller.BaseController;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController extends BaseController {


    @GetMapping("/get-payment")
    public String getPayment() throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Payment not found");
    }
}
