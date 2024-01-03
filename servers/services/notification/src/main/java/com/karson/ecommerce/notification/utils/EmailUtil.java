package com.karson.ecommerce.notification.utils;


import com.karson.ecommerce.notification.dtos.KeyValue;
import com.karson.ecommerce.notification.entities.EmailTemplate;

import java.util.List;

public class EmailUtil {
    private EmailUtil() {}
    public static void mapValueToTemplate(EmailTemplate emailTemplate, List<KeyValue> keyValueList) {
        String subject = emailTemplate.getSubject();
        String message = emailTemplate.getMessage();

        for (KeyValue keyValue : keyValueList) {
            subject = mapValueToKey(subject, keyValue.getKey(), keyValue.getValue());
            message = mapValueToKey(message, keyValue.getKey(), keyValue.getValue());
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
