package org.example.orchidbe.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // EXCHANGES
    public static final String ACCOUNT_EXCHANGE = "accountExchange";
    public static final String ORCHID_EXCHANGE = "orchidExchange";
    public static final String ORDER_EXCHANGE = "orderExchange";

    // QUEUES for the account events
    public static final String ACCOUNT_CREATED_QUEUE = "accountCreatedQueue";
    public static final String ACCOUNT_UPDATED_QUEUE = "accountUpdatedQueue";
    public static final String ACCOUNT_BLOCKED_QUEUE = "accountBlockedQueue";
    public static final String ACCOUNT_UNBLOCKED_QUEUE = "accountUnblockedQueue";

    // ROUTING KEYS for the account events
    public static final String ACCOUNT_ROUTING_KEY_CREATED = "account.created";
    public static final String ACCOUNT_ROUTING_KEY_UPDATED = "account.updated";
    public static final String ACCOUNT_ROUTING_KEY_BLOCKED = "account.blocked";
    public static final String ACCOUNT_ROUTING_KEY_UNBLOCKED = "account.unblocked";

    // QUEUES for the orchid events
    public static final String ORCHID_CREATED_QUEUE = "orchidCreatedQueue";
    public static final String ORCHID_UPDATED_QUEUE = "orchidUpdatedQueue";
    public static final String ORCHID_DISABLE_QUEUE = "orchidDisableQueue";
    public static final String ORCHID_ENABLE_QUEUE = "orchidEnableQueue";

    // ROUTING KEYS for the orchid events
    public static final String ORCHID_ROUTING_KEY_CREATED = "orchid.created";
    public static final String ORCHID_ROUTING_KEY_UPDATED = "orchid.updated";
    public static final String ORCHID_ROUTING_KEY_DISABLE = "orchid.disable";
    public static final String ORCHID_ROUTING_KEY_ENABLE = "orchid.enable";

    // QUEUES for the order events
    public static final String ORDER_CREATED_QUEUE = "orderCreatedQueue";
    public static final String ORDER_STATUS_UPDATED_QUEUE = "orderStatusUpdatedQueue";

    //ROUTING KEYS for the order events
    public static final String ORDER_ROUTING_KEY_CREATED = "order.created";
    public static final String ORDER_ROUTING_KEY_STATUS_UPDATED = "order.status.updated";

    @Bean
    public TopicExchange accountExchange() {
        return new TopicExchange(ACCOUNT_EXCHANGE);
    }

    @Bean
    public TopicExchange orchidExchange() {
        return new TopicExchange(ORCHID_EXCHANGE);
    }

    @Bean
    public TopicExchange orderExchange() {return new TopicExchange(ORDER_EXCHANGE);}

    @Bean
    public Queue accountCreatedQueue() {
        return new Queue(ACCOUNT_CREATED_QUEUE, false);
    }

    @Bean
    public Queue accountUpdatedQueue() {
        return new Queue(ACCOUNT_UPDATED_QUEUE, false);
    }

    @Bean
    public Queue accountBlockedQueue() {
        return new Queue(ACCOUNT_BLOCKED_QUEUE, false);
    }

    @Bean
    public Queue accountUnblockedQueue() {
        return new Queue(ACCOUNT_UNBLOCKED_QUEUE, false);
    }

    @Bean
    public Binding accountCreatedBinding(Queue accountCreatedQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(accountCreatedQueue).to(accountExchange).with(ACCOUNT_ROUTING_KEY_CREATED);
    }

    @Bean
    public Binding accountUpdatedBinding(Queue accountUpdatedQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(accountUpdatedQueue).to(accountExchange).with(ACCOUNT_ROUTING_KEY_UPDATED);
    }

    @Bean
    public Binding accountBlockedBinding(Queue accountBlockedQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(accountBlockedQueue).to(accountExchange).with(ACCOUNT_ROUTING_KEY_BLOCKED);
    }

    @Bean
    public Binding accountUnblockedBinding(Queue accountUnblockedQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(accountUnblockedQueue).to(accountExchange).with(ACCOUNT_ROUTING_KEY_UNBLOCKED);
    }

    @Bean
    public Queue orchidCreatedQueue() {
        return new Queue(ORCHID_CREATED_QUEUE, false);
    }

    @Bean
    public Queue orchidUpdatedQueue() {
        return new Queue(ORCHID_UPDATED_QUEUE, false);
    }

    @Bean Queue orchidDisableQueue() {
        return new Queue(ORCHID_DISABLE_QUEUE, false);
    }

    @Bean Queue orchidEnableQueue() {
        return new Queue(ORCHID_ENABLE_QUEUE, false);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE, false);
    }

    @Bean
    public Queue orderStatusUpdatedQueue() {
        return new Queue(ORDER_STATUS_UPDATED_QUEUE, false);
    }

    @Bean
    public Binding orchidCreatedBinding(Queue orchidCreatedQueue) {
        return BindingBuilder.bind(orchidCreatedQueue).to(orchidExchange()).with(ORCHID_ROUTING_KEY_CREATED);
    }

    @Bean
    public Binding orchidUpdatedBinding(Queue orchidUpdatedQueue) {
        return BindingBuilder.bind(orchidUpdatedQueue).to(orchidExchange()).with(ORCHID_ROUTING_KEY_UPDATED);
    }

    @Bean
    public Binding orchidDisableBiding(Queue orchidDisableQueue) {
        return BindingBuilder.bind(orchidDisableQueue).to(orchidExchange()).with(ORCHID_ROUTING_KEY_DISABLE);
    }

    @Bean
    public Binding orchidEnableBiding(Queue orchidEnableQueue) {
        return BindingBuilder.bind(orchidEnableQueue).to(orchidExchange()).with(ORCHID_ROUTING_KEY_ENABLE);
    }

    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue) {
        return BindingBuilder.bind(orderCreatedQueue).to(orderExchange()).with(ORDER_ROUTING_KEY_CREATED);
    }

    @Bean
    public Binding orderStatusUpdatedBinding(Queue orderStatusUpdatedQueue) {
        return BindingBuilder.bind(orderStatusUpdatedQueue).to(orderExchange()).with(ORDER_ROUTING_KEY_STATUS_UPDATED);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
}