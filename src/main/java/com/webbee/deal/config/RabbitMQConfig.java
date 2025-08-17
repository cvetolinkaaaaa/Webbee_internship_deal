
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация RabbitMQ для обработки сообщений о контрагентах.
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String CONTRACTORS_EXCHANGE = "contractors_contractor_exchange";
    public static final String DEALS_CONTRACTOR_QUEUE = "deals_contractor_queue";
    public static final String CONTRACTOR_ROUTING_KEY = "contractor.updated";
    
    // Dead letter exchange и queue для "мертвых" сообщений сервиса сделок
    public static final String DEALS_DEAD_EXCHANGE = "deals_dead_exchange";
    public static final String DEALS_DEAD_QUEUE = "deals_contractor_dead_queue";
    public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter";

    @Bean
    public TopicExchange contractorsExchange() {
        return new TopicExchange(CONTRACTORS_EXCHANGE);
    }

    @Bean
    public TopicExchange dealsDeadExchange() {
        return new TopicExchange(DEALS_DEAD_EXCHANGE);
    }

    @Bean
    public Queue dealsContractorQueue() {
        return QueueBuilder
                .durable(DEALS_CONTRACTOR_QUEUE)
                .withArgument("x-dead-letter-exchange", DEALS_DEAD_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue dealsDeadQueue() {
        return new Queue(DEALS_DEAD_QUEUE, true);
    }

    @Bean
    public Binding dealsContractorBinding() {
        return BindingBuilder
                .bind(dealsContractorQueue())
                .to(contractorsExchange())
                .with(CONTRACTOR_ROUTING_KEY);
    }

    @Bean
    public Binding dealsDeadBinding() {
        return BindingBuilder
                .bind(dealsDeadQueue()) // Исправлено: было deadsDeadQueue()
                .to(dealsDeadExchange())
                .with(DEAD_LETTER_ROUTING_KEY);
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
}