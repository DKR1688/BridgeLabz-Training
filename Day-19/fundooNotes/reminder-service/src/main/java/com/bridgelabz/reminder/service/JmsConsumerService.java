package com.bridgelabz.reminder.service;

import com.bridgelabz.reminder.dto.PasswordResetMessage;
import com.bridgelabz.reminder.dto.ReminderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JmsConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(JmsConsumerService.class);

    private final List<ReminderMessage> receivedReminders = new ArrayList<>();
    private final List<PasswordResetMessage> receivedRecoveryMessages = new ArrayList<>();

    @JmsListener(destination = "fundoonotes.reminders.queue")
    public void receiveReminder(ReminderMessage message) {
        logger.info("JMS Consumer: Received asynchronous reminder for noteId: {}, userId: {}, time: {}",
                message.getNoteId(), message.getUserId(), message.getReminderTime());
        receivedReminders.add(message);
    }

    @JmsListener(destination = "fundoonotes.recovery.queue")
    public void receivePasswordRecovery(PasswordResetMessage message) {
        logger.info("JMS Consumer: Received password recovery dispatch for email: {}, resetToken: {}",
                message.getEmail(), message.getToken());
        receivedRecoveryMessages.add(message);
    }

    public List<ReminderMessage> getReceivedReminders() {
        return receivedReminders;
    }

    public List<PasswordResetMessage> getReceivedRecoveryMessages() {
        return receivedRecoveryMessages;
    }
}
