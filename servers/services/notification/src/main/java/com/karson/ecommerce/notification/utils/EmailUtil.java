package com.karson.ecommerce.notification.utils;


import com.karson.ecommerce.notification.entities.EmailTemplate;

import java.util.Map;

public class EmailUtil {
    private EmailUtil() {}
    public static void mapValueToTemplate(EmailTemplate emailTemplate, Map<String, String> parameters) {
        String subject = emailTemplate.getSubject();
        String message = emailTemplate.getMessage();

        for (String key : parameters.keySet()) {
            subject = mapValueToKey(subject, key, parameters.get(key));
            message = mapValueToKey(message, key, parameters.get(key));
        }

        emailTemplate.setMessage(message);
        emailTemplate.setSubject(subject);
    }

    private static String mapValueToKey(String message, String key, String value) {
        final String replacer = "{" + key + "}";
        if (message.contains(replacer)) {
            message = message.replace(replacer, value);
        }
        return message;
    }
}
