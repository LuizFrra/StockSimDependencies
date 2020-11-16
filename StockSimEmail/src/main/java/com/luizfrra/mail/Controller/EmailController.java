package com.luizfrra.mail.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizfrra.mail.Entities.Message;
import com.luizfrra.mail.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    public EmailService emailService;

    @PostMapping
    public ResponseEntity sendEmail(@RequestBody Message message) throws JsonProcessingException {

        if(message.isValid()) {}
            emailService.sendSimpleMessage(message);

        return new ResponseEntity(message, HttpStatus.OK);
    }
}
