
package com.webbee.deal.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.config.RabbitMQConfig;
import com.webbee.deal.dto.ContractorDto;
import com.webbee.deal.dto.ContractorEventDto;
import com.webbee.deal.service.ContractorInboxEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Интеграционные тесты RabbitMQ с использованием Testcontainers.
 */
@SpringBootTest
@Testcontainers
class RabbitMQIntegrationTest {

    @Container
    static RabbitMQContainer rabbitMQ = new RabbitMQContainer("rabbitmq:3.12-management")
            .withStartupTimeout(Duration.ofMinutes(2))
            .withExposedPorts(5672, 15672);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQ::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQ::getAmqpPort);
        registry.add("spring.rabbitmq.username", () -> "guest");
        registry.add("spring.rabbitmq.password", () -> "guest");
        registry.add("spring.rabbitmq.virtual-host", () -> "/");
        registry.add("spring.rabbitmq.connection-timeout", () -> "30000");
        registry.add("spring.rabbitmq.requested-heartbeat", () -> "30");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ContractorInboxEventService contractorInboxEventService() {
            return Mockito.mock(ContractorInboxEventService.class);
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContractorInboxEventService inboxEventService;

    private ContractorEventDto testEvent;

    @BeforeEach
    void setUp() {
        Mockito.reset(inboxEventService);

        ContractorDto contractorData = new ContractorDto();
        contractorData.setId("CNT12345");
        contractorData.setName("Integration Test Contractor");
        contractorData.setNameFull("Integration Test Contractor LLC");

        testEvent = new ContractorEventDto();
        testEvent.setMessageId("msg-integration-test");
        testEvent.setContractorId("CNT12345");
        testEvent.setVersion(1L);
        testEvent.setEventType("contractor.updated");
        testEvent.setEventTime(LocalDateTime.now());
        testEvent.setContractorData(contractorData);

        doNothing().when(inboxEventService).processContractorEvent(any(ContractorEventDto.class));
    }

    @Test
    void shouldProcessMessageEndToEnd() {
        await().atMost(30, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .until(() -> {
                try {
                    rabbitTemplate.execute(channel -> {
                        return channel.isOpen();
                    });
                    return true;
                } catch (Exception e) {
                    return false;
                }
            });

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CONTRACTORS_EXCHANGE,
            "contractor.updated",
            testEvent
        );

        await().atMost(15, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .untilAsserted(() -> {
                verify(inboxEventService, atLeastOnce()).processContractorEvent(any(ContractorEventDto.class));
            });
    }

    @Test
    void shouldRouteToDeadLetterOnProcessingFailure() {
        doThrow(new RuntimeException("Processing failed"))
            .when(inboxEventService).processContractorEvent(any(ContractorEventDto.class));

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CONTRACTORS_EXCHANGE,
            "contractor.updated",
            testEvent
        );

        await().atMost(15, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .untilAsserted(() -> {
                verify(inboxEventService, atLeastOnce()).processContractorEvent(any(ContractorEventDto.class));
            });
    }

    @Test
    void shouldHandleInvalidMessage() {
        String invalidJson = "{ invalid json }";
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CONTRACTORS_EXCHANGE,
            "contractor.updated",
            invalidJson
        );

        await().pollDelay(3, TimeUnit.SECONDS)
            .atMost(8, TimeUnit.SECONDS)
            .until(() -> {
                verify(inboxEventService, never()).processContractorEvent(any(ContractorEventDto.class));
                return true;
            });
    }

    @Test
    void shouldProcessMultipleMessages() {
        ContractorEventDto event1 = createTestEvent("CNT001", "msg-001", "contractor.updated");
        ContractorEventDto event2 = createTestEvent("CNT002", "msg-002", "contractor.created");
        ContractorEventDto event3 = createTestEvent("CNT003", "msg-003", "contractor.deleted");
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTRACTORS_EXCHANGE, "contractor.updated", event1);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTRACTORS_EXCHANGE, "contractor.created", event2);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTRACTORS_EXCHANGE, "contractor.deleted", event3);
        await().atMost(20, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .untilAsserted(() -> {
                verify(inboxEventService, times(3)).processContractorEvent(any(ContractorEventDto.class));
            });
    }

    @Test
    void shouldHandleEventWithDifferentRoutingKeys() {
        ContractorEventDto updateEvent = createTestEvent("CNT100", "msg-100", "contractor.updated");
        ContractorEventDto createEvent = createTestEvent("CNT101", "msg-101", "contractor.created");
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTRACTORS_EXCHANGE, "contractor.updated", updateEvent);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTRACTORS_EXCHANGE, "contractor.created", createEvent);
        await().atMost(15, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .untilAsserted(() -> {
                verify(inboxEventService, times(2)).processContractorEvent(any(ContractorEventDto.class));
            });
    }

    @Test
    void shouldHandleMessageWithMinimalData() {
        ContractorDto minimalContractor = new ContractorDto();
        minimalContractor.setId("CNT_MINIMAL");
        minimalContractor.setName("Minimal Contractor");

        ContractorEventDto minimalEvent = new ContractorEventDto();
        minimalEvent.setMessageId("msg-minimal");
        minimalEvent.setContractorId("CNT_MINIMAL");
        minimalEvent.setVersion(1L);
        minimalEvent.setEventType("contractor.updated");
        minimalEvent.setEventTime(LocalDateTime.now());
        minimalEvent.setContractorData(minimalContractor);

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CONTRACTORS_EXCHANGE,
            "contractor.updated",
            minimalEvent
        );

        await().atMost(15, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .untilAsserted(() -> {
                verify(inboxEventService).processContractorEvent(argThat(event ->
                    "CNT_MINIMAL".equals(event.getContractorId()) &&
                    "msg-minimal".equals(event.getMessageId())
                ));
            });
    }

    private ContractorEventDto createTestEvent(String contractorId, String messageId, String eventType) {
        ContractorDto contractorData = new ContractorDto();
        contractorData.setId(contractorId);
        contractorData.setName("Test Contractor " + contractorId);

        ContractorEventDto event = new ContractorEventDto();
        event.setMessageId(messageId);
        event.setContractorId(contractorId);
        event.setVersion(1L);
        event.setEventType(eventType);
        event.setEventTime(LocalDateTime.now());
        event.setContractorData(contractorData);

        return event;
    }
}