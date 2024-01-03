package com.karson.ecommerce.notification;

import com.karson.ecommerce.notification.dtos.MailDto;
import com.karson.ecommerce.notification.entities.EmailTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EmailTemplateMapper {
    EmailTemplate mapToEmailTemplate(MailDto mailDto);
    MailDto mapToEmailDto(EmailTemplate mailDto);
}
