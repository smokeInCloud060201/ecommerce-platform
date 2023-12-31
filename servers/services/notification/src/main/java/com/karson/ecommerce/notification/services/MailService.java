package com.karson.ecommerce.notification.services;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.notification.dtos.MailDto;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MailService {
    void sendEmail(String emailAddress, MailDto mailDto) throws ResourceNotFoundException, MessagingException;
    MailDto upsertEmailTemplate(MailDto mailDto);
    void deleteEmailTemplate(Long emailId);
    List<MailDto> getAllEmailTemplates(SearchDto searchDto);
}
