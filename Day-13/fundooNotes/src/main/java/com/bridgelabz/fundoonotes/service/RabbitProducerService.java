package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.config.RabbitConfig;
import com.bridgelabz.fundoonotes.dto.NoteSharedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitProducerService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitProducerService.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducerService(@Autowired(required = false) RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNoteSharedEvent(NoteSharedMessage message) {
        if (rabbitTemplate != null) {
            try {
                logger.info("RabbitProducer: Publishing note.shared event for noteId={} to exchange={}",
                        message.noteId(), RabbitConfig.NOTES_TOPIC_EXCHANGE);
                rabbitTemplate.convertAndSend(RabbitConfig.NOTES_TOPIC_EXCHANGE, RabbitConfig.ROUTING_KEY_NOTE_SHARED,
                        message);
            } catch (Exception ex) {
                logger.warn("RabbitProducer: Could not send message to RabbitMQ (broker may be offline): {}",
                        ex.getMessage());
            }
        } else {
            logger.warn("RabbitProducer: RabbitTemplate is null, skipping message send");
        }
    }

    public void sendNoteDeletedEvent(int noteId, int userId) {
        if (rabbitTemplate != null) {
            try {
                Map<String, Object> payload = Map.of(
                        "noteId", noteId,
                        "userId", userId,
                        "action", "DELETED",
                        "timestamp", java.time.LocalDateTime.now().toString());
                logger.info("RabbitProducer: Publishing note.deleted event for noteId={} to exchange={}",
                        noteId, RabbitConfig.NOTES_TOPIC_EXCHANGE);
                rabbitTemplate.convertAndSend(RabbitConfig.NOTES_TOPIC_EXCHANGE, RabbitConfig.ROUTING_KEY_NOTE_DELETED,
                        payload);
            } catch (Exception ex) {
                logger.warn("RabbitProducer: Could not send note.deleted event to RabbitMQ: {}", ex.getMessage());
            }
        }
    }

    public void broadcastNoteDeletedFanout(int noteId, int userId) {
        if (rabbitTemplate != null) {
            try {
                Map<String, Object> payload = Map.of(
                        "noteId", noteId,
                        "userId", userId,
                        "action", "DELETED_FANOUT",
                        "timestamp", java.time.LocalDateTime.now().toString());
                logger.info("RabbitProducer: Broadcasting note deletion to fanout exchange={}",
                        RabbitConfig.NOTES_FANOUT_EXCHANGE);
                rabbitTemplate.convertAndSend(RabbitConfig.NOTES_FANOUT_EXCHANGE, "", payload);
            } catch (Exception ex) {
                logger.warn("RabbitProducer: Could not broadcast to fanout exchange: {}", ex.getMessage());
            }
        }
    }
}
