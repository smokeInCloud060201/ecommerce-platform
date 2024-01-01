package com.karson.ecommerce.notification.controllers;

import com.karson.ecommerce.notification.models.MailStructure;
import com.karson.ecommerce.notification.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailService mailService;


    @PostMapping("/send/{mailAddress}")
    public String sendEmail(@PathVariable String mailAddress, @RequestBody MailStructure mailStructure) {
        mailService.sendEmail(mailAddress, mailStructure);
        return "successfully";
    }
}
