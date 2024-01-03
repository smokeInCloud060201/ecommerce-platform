package com.karson.ecommerce.crmapi.clients.notifications.email;

import com.karson.ecommerce.common.enums.NotificationEmailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private NotificationEmailType emailType;
}
