package com.karson.ecommerce.crmapi.services;

import com.karson.ecommerce.crmapi.entity.User;

public interface NotificationService {

    void sendOtpMessage(User user);
}
