package com.digdatacodes.JournalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetOtp(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Aura Journal password reset code");
        message.setText(
                "Your password reset code is: " + otp + "\n\n" +
                        "This code expires in 10 minutes.\n\n" +
                        "If you didn't request this, you can safely ignore this email."
        );
        mailSender.send(message);
    }
}