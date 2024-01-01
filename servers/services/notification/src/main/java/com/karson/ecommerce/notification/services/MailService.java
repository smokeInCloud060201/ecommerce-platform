package com.karson.ecommerce.notification.services;

import com.karson.ecommerce.notification.models.MailStructure;

public interface MailService {
    void sendEmail(String emailAddress, MailStructure mailStructure);
}
