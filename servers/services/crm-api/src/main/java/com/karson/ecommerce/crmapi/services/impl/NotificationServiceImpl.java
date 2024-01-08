package com.karson.ecommerce.crmapi.services.impl;

import com.karson.ecommerce.crmapi.clients.notifications.email.EmailClient;
import com.karson.ecommerce.crmapi.clients.notifications.email.EmailDto;
import com.karson.ecommerce.crmapi.entity.OTP;
import com.karson.ecommerce.crmapi.entity.User;
import com.karson.ecommerce.crmapi.enums.NotificationEmailType;
import com.karson.ecommerce.crmapi.repositories.OTPRepository;
import com.karson.ecommerce.crmapi.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final EmailClient emailClient;
    private final OTPRepository otpRepository;
    private static final String OTP_MAIL_KEY = "otp";
    @Async
    @Override
    public void sendOtpMessage(User user) {
        Map<String, String> parameters = new HashMap<>();
        String otpCode = generateOTP();
        parameters.put(OTP_MAIL_KEY, otpCode);

        OTP otp = new OTP();
        otp.setCode(otpCode);
        otp.setUserOtp(user);
        otp.setExpiredAt(LocalDateTime.now().plusMinutes(3));
        EmailDto emailTemplate = EmailDto.builder()
                .emailType(NotificationEmailType.OTP)
                .parameters(parameters)
                .build();
        emailClient.sendEmail(user.getEmail(), emailTemplate);

        otpRepository.save(otp);

    }

    private String generateOTP() {
        int otpLength = 6;
        StringBuilder otpBuilder = new StringBuilder();

        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();

            for (int i = 0; i < otpLength; i++) {
                int digit = secureRandom.nextInt(10);
                otpBuilder.append(digit);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }

        return otpBuilder.toString();
    }
}
