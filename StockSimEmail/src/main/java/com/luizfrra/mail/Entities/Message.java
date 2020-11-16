package com.luizfrra.mail.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class Message {

    public String to;

    public String text;

    public String subject;

    public String id;

    public Message(String to, String text, String subject) {
        this.to = to;
        this.text = text;
        this.subject = subject;
    }

    @JsonIgnore
    public SimpleMailMessage convertTOMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(subject);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(text);
        return simpleMailMessage;
    }

    public boolean isValid() {
        return !(StringUtils.isEmpty(to.trim()) || StringUtils.isEmpty(text.trim()) || StringUtils.isEmpty(subject.trim()));
    }

    public void setId() {
        id = UUID.randomUUID().toString();
    }
}
