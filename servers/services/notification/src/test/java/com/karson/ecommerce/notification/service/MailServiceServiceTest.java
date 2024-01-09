package com.karson.ecommerce.notification.service;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.notification.dtos.MailDto;
import com.karson.ecommerce.notification.entities.EmailTemplate;
import com.karson.ecommerce.notification.enums.NotificationEmailType;
import com.karson.ecommerce.notification.mapper.EmailTemplateMapper;
import com.karson.ecommerce.notification.repositories.EmailTemplateRepository;
import com.karson.ecommerce.notification.services.impl.MailServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MailServiceServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailTemplateRepository emailTemplateRepository;

    @Mock
    private EmailTemplateMapper emailTemplateMapper;

    @InjectMocks
    private MailServiceImpl mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmailValidDataEmailSent() throws ResourceNotFoundException, MessagingException {
        // Arrange
        MailDto mailDto = new MailDto();
        mailDto.setEmailType(NotificationEmailType.OTP);
        mailDto.setParameters(new HashMap<>());
        mailDto.setCcList(new ArrayList<>());
        mailDto.setReceivers(new ArrayList<>());

        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setEmailType(NotificationEmailType.OTP);
        emailTemplate.setSubject("Subject");
        emailTemplate.setMessage("Message");

        when(emailTemplateRepository.findByEmailType(mailDto.getEmailType().name())).thenReturn(Optional.of(emailTemplate));
        when(emailTemplateMapper.mapToEmailDto(emailTemplate)).thenReturn(mailDto);

        // Act
        mailService.sendEmail("test@example.com", mailDto);

        // Assert
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void upsertEmailTemplateValidDataReturnsMailDto() {
        // Arrange
        MailDto mailDto = new MailDto();
        EmailTemplate emailTemplate = new EmailTemplate();

        when(emailTemplateMapper.mapToEmailTemplate(mailDto)).thenReturn(emailTemplate);
        when(emailTemplateRepository.save(emailTemplate)).thenReturn(emailTemplate);
        when(emailTemplateMapper.mapToEmailDto(emailTemplate)).thenReturn(mailDto);

        // Act
        MailDto result = mailService.upsertEmailTemplate(mailDto);

        // Assert
        assertEquals(mailDto, result);
    }

    @Test
    void deleteEmailTemplateValidIdNoExceptionThrown() {
        // Arrange
        Long emailId = 1L;
        when(emailTemplateRepository.findById(any())).thenReturn(Optional.of(new EmailTemplate()));
        // Act
        assertDoesNotThrow(() -> mailService.deleteEmailTemplate(emailId));
    }

    @Test
    void getAllEmailTemplatesValidDataReturnsList() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        List<EmailTemplate> emailTemplates = new ArrayList<>();
        List<MailDto> expectedMailDtoList = new ArrayList<>();

        when(emailTemplateRepository.findAll()).thenReturn(emailTemplates);
        when(emailTemplateMapper.mapToEmailDto(any(EmailTemplate.class))).thenReturn(new MailDto());

        // Act
        List<MailDto> result = mailService.getAllEmailTemplates(searchDto);

        // Assert
        assertEquals(expectedMailDtoList, result);
    }
}
