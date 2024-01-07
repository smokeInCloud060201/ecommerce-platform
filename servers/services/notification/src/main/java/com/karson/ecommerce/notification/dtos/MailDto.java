package com.karson.ecommerce.notification.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.karson.ecommerce.notification.enums.NotificationEmailType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailDto {
    private String id;
    private NotificationEmailType emailType;
    private String subject;
    private String message;
    private Map<String, String> parameters;
    private List<String> receivers;
    private List<String> ccList;
}
