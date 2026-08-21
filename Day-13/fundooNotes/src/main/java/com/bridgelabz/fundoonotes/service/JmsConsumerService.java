package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.config.JmsConfig;
import com.bridgelabz.fundoonotes.dto.PasswordResetMessage;
import com.bridgelabz.fundoonotes.dto.ReminderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

@Service
public class JmsConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(JmsConsumerService.class);

    // Track processed messages for testing and verification
    private final List<ReminderMessage> receivedReminders = new CopyOnWriteArrayList<>();
    private final List<PasswordResetMessage> receivedResets = new CopyOnWriteArrayList<>();

    @JmsListener(destination = JmsConfig.REMINDERS_QUEUE)
    public void processReminder(ReminderMessage message) {
        logger.info("JMS Consumer: [Thread: {}] Processing reminder for noteId={}, title='{}', time={}",
                Thread.currentThread().getName(), message.noteId(), message.noteTitle(), message.reminderTime());
        receivedReminders.add(message);
    }

    @JmsListener(destination = JmsConfig.PASSWORD_RESET_QUEUE)
    public void processPasswordReset(PasswordResetMessage message) {
        logger.info("JMS Consumer: [Thread: {}] Simulating password reset email delivery to email={}",
                Thread.currentThread().getName(), message.email());
        receivedResets.add(message);
    }

    public List<ReminderMessage> getReceivedReminders() {
        return receivedReminders;
    }

    public List<PasswordResetMessage> getReceivedResets() {
        return receivedResets;
    }

    public void clear() {
        receivedReminders.clear();
        receivedResets.clear();
    }
}
