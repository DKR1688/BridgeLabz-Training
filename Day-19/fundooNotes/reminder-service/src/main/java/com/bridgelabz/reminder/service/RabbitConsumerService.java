package com.bridgelabz.reminder.service;

import com.bridgelabz.reminder.config.RabbitConfig;
import com.bridgelabz.reminder.dto.NoteSharedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumerService.class);

    @RabbitListener(queues = RabbitConfig.NOTE_CREATED_QUEUE)
    public void receiveNoteCreated(Map<String, Object> note) {
        logger.info("RabbitMQ Consumer [note.created.queue]: Received note created event: {}", note);
    }

    @RabbitListener(queues = RabbitConfig.NOTE_SHARED_QUEUE)
    public void receiveNoteShared(NoteSharedMessage message) {
        logger.info("RabbitMQ Consumer [note.shared.queue]: Received note shared event: noteId={}, ownerId={}, collaboratorId={}",
                message.getNoteId(), message.getOwnerId(), message.getCollaboratorId());
    }

    @RabbitListener(queues = RabbitConfig.NOTE_DELETED_QUEUE)
    public void receiveNoteDeleted(Map<String, Object> payload) {
        logger.info("RabbitMQ Consumer [note.deleted.queue]: Received note deleted event: {}", payload);
    }
}
