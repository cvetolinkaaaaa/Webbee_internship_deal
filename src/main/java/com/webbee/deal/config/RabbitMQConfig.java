package com.webbee.deal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация RabbitMQ для обработки сообщений о контрагентах.
 */
@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final ObjectMapper objectMapper;

    public static final String CONTRACTORS_EXCHANGE = "contractors_contractor_exchange";
    public static final String DEALS_CONTRACTOR_QUEUE = "deals_contractor_queue";
    public static final String DEALS_DEAD_EXCHANGE = "deals_dead_exchange";
    public static final String DEALS_CONTRACTOR_DEAD_QUEUE = "deals_contractor_dead_queue";
    public static final String DEAL_CONTRACTOR_DEAD_EXCHANGE = "deal_contractor_dead_exchange";
    public static final int RETRY_TTL = 5 * 60 * 1000;

    @Bean
    public TopicExchange contractorsExchange() {
        return new TopicExchange(CONTRACTORS_EXCHANGE);
    }

    @Bean
    public TopicExchange dealsDeadExchange() {
        return new TopicExchange(DEALS_DEAD_EXCHANGE);
    }

    @Bean
    public TopicExchange dealContractorDeadExchange() {
        return new TopicExchange(DEAL_CONTRACTOR_DEAD_EXCHANGE);
    }

    @Bean
    public Queue dealsContractorQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DEALS_DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key", "contractor.dead");
        return QueueBuilder.durable(DEALS_CONTRACTOR_QUEUE)
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue dealsContractorDeadQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", RETRY_TTL);
        args.put("x-dead-letter-exchange", DEAL_CONTRACTOR_DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key", "contractor.retry");
        return QueueBuilder.durable(DEALS_CONTRACTOR_DEAD_QUEUE)
                .withArguments(args)
                .build();
    }

    @Bean
    public Binding dealsContractorBinding() {
        return BindingBuilder
                .bind(dealsContractorQueue())
                .to(contractorsExchange())
                .with("contractor.*");
    }

    @Bean
    public Binding dealsContractorDeadBinding() {
        return BindingBuilder
                .bind(dealsContractorDeadQueue())
                .to(dealsDeadExchange())
                .with("contractor.dead");
    }

    @Bean
    public Binding dealContractorRetryBinding() {
        return BindingBuilder
                .bind(dealsContractorQueue())
                .to(dealContractorDeadExchange())
                .with("contractor.retry");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        converter.setClassMapper(null);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    /**
     * Конфигурация контейнера для ручной обработки сообщений.
     * Отключаем автоматическую конверсию для полного контроля над десериализацией.
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(null);
        factory.setDefaultRequeueRejected(false);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(
            new ConditionalRejectingErrorHandler.DefaultExceptionStrategy()));

        return factory;
    }

    /**
     * Альтернативная фабрика для слушателей, которым нужна автоматическая конверсия
     * (например, для DeadLetterListener).
     */
    @Bean
    public SimpleRabbitListenerContainerFactory autoConvertListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setDefaultRequeueRejected(false);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(
            new ConditionalRejectingErrorHandler.DefaultExceptionStrategy()));
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory cf) {
        RabbitAdmin admin = new RabbitAdmin(cf);
        admin.setAutoStartup(true);
        return admin;
    }

}
