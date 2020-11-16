package com.luizfrra.mail.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizfrra.mail.Entities.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    public String fromEmail;

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    public ObjectMapper objectMapper;

    public void sendSimpleMessage(Message message) throws JsonProcessingException {
        message.setId();
        rabbitTemplate.convertAndSend("stocksim.email", "", objectMapper.writeValueAsBytes(message));
    }
}
