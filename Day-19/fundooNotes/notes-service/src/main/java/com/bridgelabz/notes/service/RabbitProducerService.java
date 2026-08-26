package com.bridgelabz.notes.service;

import com.bridgelabz.notes.config.RabbitConfig;
import com.bridgelabz.notes.dto.NoteResponse;
import com.bridgelabz.notes.dto.NoteSharedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitProducerService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitProducerService.class);

    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    public void publishNoteCreated(NoteResponse note) {
        if (rabbitTemplate != null) {
            try {
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_NOTE_CREATED, note);
                logger.info("RabbitMQ: Published note created event for noteId: {}", note.getNoteId());
            } catch (Exception e) {
                logger.warn("RabbitMQ unavailable, skipped note created event: {}", e.getMessage());
            }
        }
    }

    public void publishNoteShared(int noteId, int ownerId, int collaboratorId) {
        if (rabbitTemplate != null) {
            try {
                NoteSharedMessage message = new NoteSharedMessage(noteId, ownerId, collaboratorId);
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_NOTE_SHARED, message);
                logger.info("RabbitMQ: Published note shared event for noteId: {} to user: {}", noteId, collaboratorId);
            } catch (Exception e) {
                logger.warn("RabbitMQ unavailable, skipped note shared event: {}", e.getMessage());
            }
        }
    }

    public void publishNoteDeleted(int noteId, int ownerId) {
        if (rabbitTemplate != null) {
            try {
                Map<String, Object> payload = Map.of("noteId", noteId, "ownerId", ownerId, "deleted", true);
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_NOTE_DELETED, payload);
                logger.info("RabbitMQ: Published note deleted event for noteId: {}", noteId);
            } catch (Exception e) {
                logger.warn("RabbitMQ unavailable, skipped note deleted event: {}", e.getMessage());
            }
        }
    }

    public void broadcastNoteDeletedFanout(int noteId) {
        if (rabbitTemplate != null) {
            try {
                rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE_NAME, "", Map.of("noteId", noteId, "broadcast", true));
                logger.info("RabbitMQ Fanout: Broadcasted note deleted event for noteId: {}", noteId);
            } catch (Exception e) {
                logger.warn("RabbitMQ unavailable, skipped fanout note deleted event: {}", e.getMessage());
            }
        }
    }
}
