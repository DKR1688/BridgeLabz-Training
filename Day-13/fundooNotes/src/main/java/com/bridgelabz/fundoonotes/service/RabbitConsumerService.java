package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.config.RabbitConfig;
import com.bridgelabz.fundoonotes.dto.NoteSharedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RabbitConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumerService.class);

    private final List<NoteSharedMessage> receivedNotifications = Collections.synchronizedList(new ArrayList<>());

    @RabbitListener(queues = RabbitConfig.COLLABORATOR_QUEUE, autoStartup = "${spring.rabbitmq.listener.auto-startup:false}")
    public void notifyCollaborator(NoteSharedMessage message) {
        logger.info("RabbitConsumer: Received collaborator notification for noteId={}, collaboratorEmail={}",
                message.noteId(), message.collaboratorEmail());
        receivedNotifications.add(message);
    }

    public List<NoteSharedMessage> getReceivedNotifications() {
        return new ArrayList<>(receivedNotifications);
    }

    public void clearNotifications() {
        receivedNotifications.clear();
    }
}
