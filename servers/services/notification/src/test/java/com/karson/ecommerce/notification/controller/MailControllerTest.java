package com.karson.ecommerce.notification.controller;

import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.notification.controllers.MailController;
import com.karson.ecommerce.notification.dtos.MailDto;
import com.karson.ecommerce.notification.services.MailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MailControllerTest {

    @Mock
    private MailService mailService;

    @InjectMocks
    private MailController mailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_ValidRequest_SuccessResponse() throws ResourceNotFoundException, MessagingException {
        // Arrange
        String mailAddress = "test@example.com";
        MailDto mailDto = new MailDto();

        // Act
        ResponseDto<String> responseEntity = mailController.sendEmail(mailAddress, mailDto);

        // Assert
        assertEquals(200, responseEntity.getStatusCode());
        assertEquals("successfully", responseEntity.getData());
        verify(mailService).sendEmail(mailAddress, mailDto);
    }

    @Test
    void upsertEmailTemplate_ValidRequest_SuccessResponse() {
        // Arrange
        MailDto mailDto = new MailDto();
        when(mailService.upsertEmailTemplate(any(MailDto.class))).thenReturn(mailDto);

        // Act
        ResponseDto<MailDto> responseEntity = mailController.upsertEmailTemplate(mailDto);

        // Assert
        assertEquals(200, responseEntity.getStatusCode());
        assertEquals(mailDto, responseEntity.getData());
        verify(mailService).upsertEmailTemplate(mailDto);
    }

    @Test
    void searchEmailTemplate_ValidRequest_SuccessResponse() {
        // Arrange
        List<MailDto> mailDtoList = Collections.singletonList(new MailDto());
        when(mailService.getAllEmailTemplates(null)).thenReturn(mailDtoList);

        // Act
        ResponseDto<List<MailDto>> responseEntity = mailController.searchEmailTemplate();

        // Assert
        assertEquals(200, responseEntity.getStatusCode());
        assertEquals(mailDtoList, responseEntity.getData());
        verify(mailService).getAllEmailTemplates(null);
    }
}
