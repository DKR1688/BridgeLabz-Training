package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ActivityLogConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityLogConsumerService.class);

    private final List<Object> receivedActivityLogs = Collections.synchronizedList(new ArrayList<>());

    @RabbitListener(queues = RabbitConfig.ACTIVITY_LOG_QUEUE, autoStartup = "${spring.rabbitmq.listener.auto-startup:false}")
    public void logActivity(Object message) {
        logger.info("ActivityLogConsumer: Activity log event received: {}", message);
        receivedActivityLogs.add(message);
    }

    public List<Object> getReceivedActivityLogs() {
        return new ArrayList<>(receivedActivityLogs);
    }

    public void clearActivityLogs() {
        receivedActivityLogs.clear();
    }
}
