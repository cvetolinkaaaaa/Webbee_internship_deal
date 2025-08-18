package com.webbee.deal.rabbitMQ;

import com.webbee.deal.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты конфигурации RabbitMQ.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.rabbitmq.host=localhost",
    "spring.rabbitmq.port=5672",
    "spring.rabbitmq.username=guest",
    "spring.rabbitmq.password=guest"
})
class RabbitMQConfigTest {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange contractorsExchange;

    @Autowired
    private TopicExchange dealsDeadExchange;

    @Autowired
    private Queue dealsContractorQueue;

    @Autowired
    private Queue dealsContractorDeadQueue;

    @Test
    void shouldCreateExchanges() {
        assertThat(contractorsExchange).isNotNull();
        assertThat(contractorsExchange.getName()).isEqualTo(RabbitMQConfig.CONTRACTORS_EXCHANGE);
        assertThat(contractorsExchange.getType()).isEqualTo("topic");

        assertThat(dealsDeadExchange).isNotNull();
        assertThat(dealsDeadExchange.getName()).isEqualTo(RabbitMQConfig.DEALS_DEAD_EXCHANGE);
        assertThat(dealsDeadExchange.getType()).isEqualTo("topic");
    }

    @Test
    void shouldCreateQueues() {
        assertThat(dealsContractorQueue).isNotNull();
        assertThat(dealsContractorQueue.getName()).isEqualTo(RabbitMQConfig.DEALS_CONTRACTOR_QUEUE);
        assertThat(dealsContractorQueue.isDurable()).isTrue();

        assertThat(dealsContractorDeadQueue).isNotNull();
        assertThat(dealsContractorDeadQueue.getName()).isEqualTo(RabbitMQConfig.DEALS_CONTRACTOR_DEAD_QUEUE);
        assertThat(dealsContractorDeadQueue.isDurable()).isTrue();
    }

    @Test
    void shouldConfigureMessageConverter() {
        assertThat(rabbitTemplate.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

    @Test
    void shouldConfigureRabbitTemplate() {
        assertThat(rabbitTemplate).isNotNull();
        assertThat(rabbitTemplate.getMessageConverter()).isNotNull();
    }
}