package com.bridgelabz.reminder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
            logger.warn("JavaMailSender not configured. Reset email to {} not sent.", toEmail);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@fundoonotes.app");
            message.setTo(toEmail);
            message.setSubject("Reset your Fundoo Notes password");
            message.setText("Click here to reset: https://fundoonotes.app/reset?token=" + resetToken);
            mailSender.send(message);
            logger.info("Real email delivered for password recovery to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Error sending password reset email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendReminderEmail(String toEmail, int noteId, LocalDateTime reminderTime) {
        if (mailSender == null) {
            logger.warn("JavaMailSender not configured. Reminder email for note {} not sent.", noteId);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("reminders@fundoonotes.app");
            message.setTo(toEmail);
            message.setSubject("Fundoo Notes Reminder: Note #" + noteId);
            message.setText("This is a reminder for your note #" + noteId + " scheduled for " + reminderTime);
            mailSender.send(message);
            logger.info("Real reminder email delivered for noteId: {} to {}", noteId, toEmail);
        } catch (Exception e) {
            logger.error("Error sending reminder email for noteId {} to {}: {}", noteId, toEmail, e.getMessage());
        }
    }
}
