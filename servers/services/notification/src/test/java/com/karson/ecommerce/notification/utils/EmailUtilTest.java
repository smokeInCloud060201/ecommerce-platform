package com.karson.ecommerce.notification.utils;

import com.karson.ecommerce.notification.entities.EmailTemplate;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailUtilTest {
    @Test
    void mapValueToTemplate() {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setSubject("Hello {name}, welcome to our website!");
        emailTemplate.setMessage("Dear {name}, welcome to our website! We are glad to have you here. If you have any questions, feel free to ask us.");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "John");

        EmailUtil.mapValueToTemplate(emailTemplate, parameters);
        assertEquals("Hello John, welcome to our website!", emailTemplate.getSubject());
        assertEquals("Dear John, welcome to our website! We are glad to have you here. If you have any questions, feel free to ask us.", emailTemplate.getMessage());
    }
}
