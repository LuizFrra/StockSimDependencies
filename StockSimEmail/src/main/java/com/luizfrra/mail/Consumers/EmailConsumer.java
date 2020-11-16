package com.luizfrra.mail.Consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizfrra.mail.Entities.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class EmailConsumer {

    @Autowired
    public ObjectMapper objectMapper;

    @Value("${spring.mail.username}")
    public String fromEmail;

    @Autowired
    public JavaMailSender mailSender;

    @RabbitListener(queues = {"emailToSend"})
    public void consumeEmail(String message) throws IOException {
        log.info("Receiving message to send.");
        Message messageObj = objectMapper.readValue(message, Message.class);

        if(messageObj.isValid()) {
            SimpleMailMessage mailMessage = messageObj.convertTOMailMessage();
            mailMessage.setFrom(fromEmail);
            mailSender.send(mailMessage);
            log.info("Message sent. " + messageObj.id);
            return;
        }

        log.info("Message is Invalid. " + messageObj.id);
    }
}
