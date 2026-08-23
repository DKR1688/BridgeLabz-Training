package com.bridgelabz.fundoonotes.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@ConditionalOnClass(RabbitTemplate.class)
public class RabbitConfig {

    public static final String NOTES_TOPIC_EXCHANGE = "notes-exchange";
    public static final String NOTES_FANOUT_EXCHANGE = "notes-fanout-exchange";

    public static final String COLLABORATOR_QUEUE = "collaborator-notify-queue";
    public static final String ACTIVITY_LOG_QUEUE = "activity-log-queue";
    public static final String AUDIT_LOG_QUEUE = "audit-log-queue";

    public static final String ROUTING_KEY_NOTE_SHARED = "note.shared";
    public static final String ROUTING_KEY_NOTE_DELETED = "note.deleted";
    public static final String ROUTING_KEY_NOTE_ALL = "note.#";

    @Bean
    public TopicExchange notesExchange() {
        return new TopicExchange(NOTES_TOPIC_EXCHANGE, true, false);
    }

    @Bean
    public FanoutExchange notesFanoutExchange() {
        return new FanoutExchange(NOTES_FANOUT_EXCHANGE, true, false);
    }

    @Bean
    public Queue collaboratorQueue() {
        return new Queue(COLLABORATOR_QUEUE, true);
    }

    @Bean
    public Queue activityLogQueue() {
        return new Queue(ACTIVITY_LOG_QUEUE, true);
    }

    @Bean
    public Queue auditLogQueue() {
        return new Queue(AUDIT_LOG_QUEUE, true);
    }

    @Bean
    public Binding collaboratorBinding(Queue collaboratorQueue, TopicExchange notesExchange) {
        return BindingBuilder.bind(collaboratorQueue)
                .to(notesExchange)
                .with(ROUTING_KEY_NOTE_SHARED);
    }

    @Bean
    public Binding activityLogBinding(Queue activityLogQueue, TopicExchange notesExchange) {
        return BindingBuilder.bind(activityLogQueue)
                .to(notesExchange)
                .with(ROUTING_KEY_NOTE_ALL);
    }

    @Bean
    public Binding auditLogBinding(Queue auditLogQueue, FanoutExchange notesFanoutExchange) {
        return BindingBuilder.bind(auditLogQueue)
                .to(notesFanoutExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        factory.setMissingQueuesFatal(false);
        factory.setDefaultRequeueRejected(false);
        factory.setAutoStartup(false);
        return factory;
    }
}
