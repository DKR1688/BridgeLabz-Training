package com.bridgelabz.userauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Autowired(required = false)
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        if (mailSender == null) {
            logger.warn("JavaMailSender is not configured. Email to {} with token {} will not be sent over SMTP.", toEmail, resetToken);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@fundoonotes.app");
            message.setTo(toEmail);
            message.setSubject("Reset your Fundoo Notes password");
            message.setText("Click here to reset your password: https://fundoonotes.app/reset?token=" + resetToken);
            mailSender.send(message);
            logger.info("Password reset email successfully dispatched to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage(), e);
        }
    }
}
