package com.bridgelabz.reminder.service;

import com.bridgelabz.reminder.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActivityLogConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityLogConsumerService.class);

    @RabbitListener(queues = RabbitConfig.ACTIVITY_LOG_QUEUE)
    public void logActivity(Map<String, Object> event) {
        logger.info("RabbitMQ Audit Consumer [activity.log.queue]: Audit logging event -> {}", event);
    }
}
