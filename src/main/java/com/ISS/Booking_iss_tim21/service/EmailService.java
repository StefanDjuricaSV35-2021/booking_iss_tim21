package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.EmailStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String fromEmail;

    public void sendEmail(String toEmail, EmailStructure emailStructure){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(emailStructure.getMessage());
        message.setSubject(emailStructure.getSubject());

        javaMailSender.send(message);
    }
}
