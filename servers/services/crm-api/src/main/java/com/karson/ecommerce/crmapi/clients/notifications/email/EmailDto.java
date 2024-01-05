package com.karson.ecommerce.crmapi.clients.notifications.email;

import com.karson.ecommerce.crmapi.enums.NotificationEmailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private NotificationEmailType emailType;
    private Map<String, String> parameters;
}
