package com.bridgelabz.reminder.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "fundoonotes.exchange";
    public static final String FANOUT_EXCHANGE_NAME = "fundoonotes.fanout";

    public static final String NOTE_CREATED_QUEUE = "note.created.queue";
    public static final String NOTE_SHARED_QUEUE = "note.shared.queue";
    public static final String NOTE_DELETED_QUEUE = "note.deleted.queue";
    public static final String ACTIVITY_LOG_QUEUE = "activity.log.queue";

    public static final String ROUTING_NOTE_CREATED = "note.event.created";
    public static final String ROUTING_NOTE_SHARED = "note.event.shared";
    public static final String ROUTING_NOTE_DELETED = "note.event.deleted";
    public static final String ROUTING_NOTE_ALL = "note.event.*";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue noteCreatedQueue() {
        return new Queue(NOTE_CREATED_QUEUE, true);
    }

    @Bean
    public Queue noteSharedQueue() {
        return new Queue(NOTE_SHARED_QUEUE, true);
    }

    @Bean
    public Queue noteDeletedQueue() {
        return new Queue(NOTE_DELETED_QUEUE, true);
    }

    @Bean
    public Queue activityLogQueue() {
        return new Queue(ACTIVITY_LOG_QUEUE, true);
    }

    @Bean
    public Binding bindNoteCreatedQueue(Queue noteCreatedQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(noteCreatedQueue).to(topicExchange).with(ROUTING_NOTE_CREATED);
    }

    @Bean
    public Binding bindNoteSharedQueue(Queue noteSharedQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(noteSharedQueue).to(topicExchange).with(ROUTING_NOTE_SHARED);
    }

    @Bean
    public Binding bindNoteDeletedQueue(Queue noteDeletedQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(noteDeletedQueue).to(topicExchange).with(ROUTING_NOTE_DELETED);
    }

    @Bean
    public Binding bindActivityLogQueue(Queue activityLogQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(activityLogQueue).to(topicExchange).with(ROUTING_NOTE_ALL);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
