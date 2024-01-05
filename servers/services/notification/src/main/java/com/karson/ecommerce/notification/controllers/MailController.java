package com.karson.ecommerce.notification.controllers;

import com.karson.ecommerce.common.dtos.ResponseDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.common.utils.DtoUtil;
import com.karson.ecommerce.notification.dtos.MailDto;
import com.karson.ecommerce.notification.services.MailService;
import com.karson.ecommerce.notification.services.impl.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailService mailService;
    private final KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public ResponseDto<String> sendEmail(@RequestParam String mailAddress, @RequestParam String mailType) throws ResourceNotFoundException {
        mailService.sendEmail(mailAddress, mailType);
        return DtoUtil.toResponseDto("successfully");
    }

    @GetMapping("/send-message")
    public String sendMessage() {
        kafkaProducer.sendMessage("Hello, Kafka!");
        return "Message sent to Kafka.";
    }

    @PostMapping("/template")
    public ResponseDto<MailDto> upsertEmailTemplate(@RequestBody MailDto mailDto) {
        MailDto mailDtoResponse = mailService.upsertEmailTemplate(mailDto);
        return DtoUtil.toResponseDto(mailDtoResponse);
    }

    @GetMapping("/template")
    public ResponseDto<List<MailDto>> searchEmailTemplate() {
        List<MailDto> mailDtoResponse = mailService.getAllEmailTemplates(null);
        return DtoUtil.toResponseDto(mailDtoResponse);
    }
}
