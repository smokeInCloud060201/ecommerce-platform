package com.karson.ecommerce.notification.services.impl;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.notification.mapper.EmailTemplateMapper;
import com.karson.ecommerce.notification.dtos.KeyValue;
import com.karson.ecommerce.notification.dtos.MailDto;
import com.karson.ecommerce.notification.entities.EmailTemplate;
import com.karson.ecommerce.notification.repositories.EmailTemplateRepository;
import com.karson.ecommerce.notification.services.MailService;
import com.karson.ecommerce.notification.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(String emailAddress, String mailType) throws ResourceNotFoundException {
        EmailTemplate emailTemplate = emailTemplateRepository.findByEmailType(mailType).orElseThrow(() -> new ResourceNotFoundException("Not found Email type"));

        switch (emailTemplate.getEmailType()) {
            case OTP:
                String randomOtp = generateOTP();
                EmailUtil.mapValueToTemplate(emailTemplate, List.of(new KeyValue("otp", randomOtp)));
                break;

            default:
                break;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(emailTemplate.getSubject());
        simpleMailMessage.setText(emailTemplate.getMessage());
        simpleMailMessage.setTo(emailAddress);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public MailDto upsertEmailTemplate(MailDto mailDto) {
        EmailTemplate emailTemplate = emailTemplateMapper.mapToEmailTemplate(mailDto);
        emailTemplate = emailTemplateRepository.save(emailTemplate);
        return emailTemplateMapper.mapToEmailDto(emailTemplate);
    }

    @Override
    public void deleteEmailTemplate(Long emailId) {

    }

    @Override
    public List<MailDto> getAllEmailTemplates(SearchDto searchDto) {
        return emailTemplateRepository.findAll().stream().map(emailTemplateMapper::mapToEmailDto).toList();

    }

    private String generateOTP() {
        int otpLength = 6;

        StringBuilder otpBuilder = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            int digit = ThreadLocalRandom.current().nextInt(10);
            otpBuilder.append(digit);
        }

        return otpBuilder.toString();
    }
}
