package com.bridgelabz.notes.service;

import com.bridgelabz.notes.dto.ReminderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsProducerService {

    private static final Logger logger = LoggerFactory.getLogger(JmsProducerService.class);
    public static final String REMINDERS_QUEUE = "fundoonotes.reminders.queue";

    @Autowired(required = false)
    private JmsTemplate jmsTemplate;

    public void sendReminderMessage(ReminderMessage message) {
        if (jmsTemplate != null) {
            try {
                jmsTemplate.convertAndSend(REMINDERS_QUEUE, message);
                logger.info("Published reminder message to queue '{}' for noteId: {}", REMINDERS_QUEUE, message.getNoteId());
            } catch (Exception e) {
                logger.warn("Could not dispatch JMS message: {}", e.getMessage());
            }
        }
    }
}
