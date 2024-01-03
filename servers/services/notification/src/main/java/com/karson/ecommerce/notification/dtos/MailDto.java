package com.karson.ecommerce.notification.dtos;

import com.karson.ecommerce.common.enums.NotificationEmailType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailDto {
    private String id;
    private NotificationEmailType emailType;
    private String subject;
    private String message;
}
