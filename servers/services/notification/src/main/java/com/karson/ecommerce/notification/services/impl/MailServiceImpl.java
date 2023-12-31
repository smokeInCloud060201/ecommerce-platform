package com.karson.ecommerce.notification.services.impl;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.notification.dtos.MailDto;
import com.karson.ecommerce.notification.entities.EmailTemplate;
import com.karson.ecommerce.notification.mapper.EmailTemplateMapper;
import com.karson.ecommerce.notification.repositories.EmailTemplateRepository;
import com.karson.ecommerce.notification.services.MailService;
import com.karson.ecommerce.notification.utils.EmailUtil;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(String emailAddress, MailDto mailDto) throws ResourceNotFoundException, MessagingException {
        EmailTemplate emailTemplate = emailTemplateRepository.findByEmailType(mailDto.getEmailType().name())
                .orElseThrow(() -> new ResourceNotFoundException("Not found Email type"));

        switch (emailTemplate.getEmailType()) {
            case OTP:
                EmailUtil.mapValueToTemplate(emailTemplate, mailDto.getParameters());
                break;

            default:
                break;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(emailTemplate.getSubject());
        simpleMailMessage.setText(emailTemplate.getMessage());
        simpleMailMessage.setTo(emailAddress);

        Address[] ccAddress = new Address[mailDto.getCcList().size()];

        for (int i = 0; i < ccAddress.length; i++) {
            ccAddress[i] = new InternetAddress(mailDto.getCcList().get(i));
        }


        for (String receiver : mailDto.getReceivers()) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setSubject(emailTemplate.getSubject());
            mimeMessage.setFrom(sender);
            mimeMessage.setRecipients(Message.RecipientType.TO, receiver);
            mimeMessage.setRecipients(Message.RecipientType.CC, ccAddress);
            mimeMessage.setText(emailTemplate.getMessage());
        }


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
}
