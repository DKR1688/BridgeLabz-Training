package com.bridgelabz.userauth.service;

import com.bridgelabz.userauth.dto.PasswordResetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsProducerService {

    private static final Logger logger = LoggerFactory.getLogger(JmsProducerService.class);
    public static final String RECOVERY_QUEUE = "fundoonotes.recovery.queue";

    @Autowired(required = false)
    private JmsTemplate jmsTemplate;

    public void sendPasswordRecoveryMessage(PasswordResetMessage message) {
        if (jmsTemplate != null) {
            try {
                jmsTemplate.convertAndSend(RECOVERY_QUEUE, message);
                logger.info("Published password recovery message to queue '{}' for email: {}", RECOVERY_QUEUE, message.getEmail());
            } catch (Exception e) {
                logger.warn("Could not dispatch password recovery JMS message: {}", e.getMessage());
            }
        }
    }
}
