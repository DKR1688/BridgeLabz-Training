package com.bridgelabz.reminder;

import com.bridgelabz.reminder.dto.PasswordResetMessage;
import com.bridgelabz.reminder.dto.ReminderMessage;
import com.bridgelabz.reminder.service.JmsConsumerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ReminderEventProcessingTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsConsumerService jmsConsumerService;

    @Test
    @DisplayName("Use Case 19: Decoupled Reminder Service consumes asynchronous JMS reminders")
    void testConsumeReminderMessage() {
        ReminderMessage reminder = new ReminderMessage(42, 10, LocalDateTime.now().plusHours(2));
        jmsTemplate.convertAndSend("fundoonotes.reminders.queue", reminder);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertFalse(jmsConsumerService.getReceivedReminders().isEmpty());
            ReminderMessage received = jmsConsumerService.getReceivedReminders().get(jmsConsumerService.getReceivedReminders().size() - 1);
            assertEquals(42, received.getNoteId());
            assertEquals(10, received.getUserId());
        });
    }

    @Test
    @DisplayName("Use Case 19: Decoupled Reminder Service consumes asynchronous password recovery events")
    void testConsumePasswordRecoveryMessage() {
        PasswordResetMessage recovery = new PasswordResetMessage("recovery@example.com", "reset-token-12345");
        jmsTemplate.convertAndSend("fundoonotes.recovery.queue", recovery);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertFalse(jmsConsumerService.getReceivedRecoveryMessages().isEmpty());
            PasswordResetMessage received = jmsConsumerService.getReceivedRecoveryMessages().get(jmsConsumerService.getReceivedRecoveryMessages().size() - 1);
            assertEquals("recovery@example.com", received.getEmail());
            assertEquals("reset-token-12345", received.getToken());
        });
    }
}
