package com.karson.ecommerce.notification.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.karson.ecommerce.notification.enums.NotificationEmailType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class MailDto {
    private String id;
    private NotificationEmailType emailType;
    private String subject;
    private String message;
    private Map<String, String> parameters = new HashMap<>();
    private List<String> receivers = new ArrayList<>();
    private List<String> ccList = new ArrayList<>();
}
