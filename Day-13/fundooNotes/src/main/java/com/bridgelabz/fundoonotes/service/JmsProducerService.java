package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.config.JmsConfig;
import com.bridgelabz.fundoonotes.dto.PasswordResetMessage;
import com.bridgelabz.fundoonotes.dto.ReminderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsProducerService {

    private static final Logger logger = LoggerFactory.getLogger(JmsProducerService.class);
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsProducerService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendReminderMessage(ReminderMessage message) {
        logger.info("JMS Producer: Sending reminder event for noteId={}, userId={}", message.noteId(),
                message.userId());
        jmsTemplate.convertAndSend(JmsConfig.REMINDERS_QUEUE, message);
    }

    public void sendPasswordResetMessage(PasswordResetMessage message) {
        logger.info("JMS Producer: Sending password reset event for email={}", message.email());
        jmsTemplate.convertAndSend(JmsConfig.PASSWORD_RESET_QUEUE, message);
    }
}
