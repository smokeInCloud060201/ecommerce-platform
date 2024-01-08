package com.karson.ecommerce.crmapi.services;

import com.karson.ecommerce.crmapi.clients.notifications.email.EmailClient;
import com.karson.ecommerce.crmapi.clients.notifications.email.EmailDto;
import com.karson.ecommerce.crmapi.entity.OTP;
import com.karson.ecommerce.crmapi.entity.User;
import com.karson.ecommerce.crmapi.repositories.OTPRepository;
import com.karson.ecommerce.crmapi.services.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NotificationServiceTest {
    @Mock
    private EmailClient emailClient;

    @Mock
    private OTPRepository otpRepository;
    @Spy
    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendOtpMessage() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        // Act
        notificationService.sendOtpMessage(user);

        // Assert
        verify(emailClient, times(1)).sendEmail(eq(user.getEmail()), any(EmailDto.class));
        verify(otpRepository, times(1)).save(any(OTP.class));
    }

}
