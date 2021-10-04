package com.marketplace.service;

import com.marketplace.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;


    public void send(List<User> userList, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        for (User user: userList){
            mailMessage.setFrom(username);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        }
    }
}
