package com.webbee.deal.config;

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
public class RabbitMQConfig {

    public static final String CONTRACTORS_EXCHANGE = "contractors_contractor_exchange";
    public static final String DEALS_CONTRACTOR_QUEUE = "deals_contractor_queue";
    
    // Dead letter exchange и queue для "мертвых" сообщений
    public static final String DEALS_DEAD_EXCHANGE = "deals_dead_exchange";
    public static final String DEALS_CONTRACTOR_DEAD_QUEUE = "deals_contractor_dead_queue";
    
    // Retry exchange для повторной обработки после таймаута
    public static final String DEAL_CONTRACTOR_DEAD_EXCHANGE = "deal_contractor_dead_exchange";
    
    // TTL для повторной попытки (5 минут в миллисекундах)
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
        args.put("x-dead-letter-routing-key", "contractor.dead"); // Синхронизировано с отправителем
        return QueueBuilder.durable(DEALS_CONTRACTOR_QUEUE)
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue dealsContractorDeadQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", RETRY_TTL); // TTL 5 минут
        args.put("x-dead-letter-exchange", DEAL_CONTRACTOR_DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key", "contractor.retry");
        return QueueBuilder.durable(DEALS_CONTRACTOR_DEAD_QUEUE)
                .withArguments(args)
                .build();
    }

    // Привязка для получения всех сообщений о контрагентах (created и updated)
    @Bean
    public Binding dealsContractorBinding() {
        return BindingBuilder
                .bind(dealsContractorQueue())
                .to(contractorsExchange())
                .with("contractor.*"); // Изменено для получения всех сообщений
    }

    @Bean
    public Binding dealsContractorDeadBinding() {
        return BindingBuilder
                .bind(dealsContractorDeadQueue())
                .to(dealsDeadExchange())
                .with("contractor.dead"); // Синхронизировано с отправителем
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
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setDefaultRequeueRejected(false);

        // Отключаем автоматическое acknowledgment для полного контроля
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        // Настраиваем обработку ошибок
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(new ConditionalRejectingErrorHandler.DefaultExceptionStrategy()));

        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory cf) {
        RabbitAdmin admin = new RabbitAdmin(cf);
        admin.setAutoStartup(true);   // обязательно
        return admin;
    }
}