package com.karson.ecommerce.notification.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.karson.ecommerce.notification.enums.NotificationEmailType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailDto {
    private String id;
    private NotificationEmailType emailType;
    private String subject;
    private String message;
    private Map<String, String> parameters;
}
