package com.webbee.deal.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.webbee.deal.dto.ContractorDto;
import com.webbee.deal.dto.ContractorEventDto;
import com.webbee.deal.service.ContractorInboxEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Тесты для ContractorMessageListener.
 */
@ExtendWith(MockitoExtension.class)
class ContractorMessageListenerTest {

    @Mock
    private ContractorInboxEventService inboxEventService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Channel channel;

    @InjectMocks
    private ContractorMessageListener contractorMessageListener;

    private ContractorEventDto validEvent;
    private Message validMessage;
    private final long deliveryTag = 1L;

    @BeforeEach
    void setUp() {
        ContractorDto contractorData = new ContractorDto();
        contractorData.setId("CNT12345");
        contractorData.setName("Test Contractor");

        validEvent = new ContractorEventDto();
        validEvent.setMessageId("msg-12345");
        validEvent.setContractorId("CNT12345");
        validEvent.setVersion(1L);
        validEvent.setEventType("contractor.updated");
        validEvent.setEventTime(LocalDateTime.now());
        validEvent.setContractorData(contractorData);

        String validJson = """
            {
                "messageId": "%s",
                "contractorId": "%s",
                "version": %d,
                "eventType": "%s",
                "eventTime": "%s",
                "contractorData": {
                    "id": "%s",
                    "name": "Test Contractor"
                }
            }
            """.formatted(
                validEvent.getMessageId(),
                validEvent.getContractorId(),
                validEvent.getVersion(),
                validEvent.getEventType(),
                validEvent.getEventTime(),
                contractorData.getId()
            );

        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        validMessage = new Message(validJson.getBytes(), properties);
    }

    @Test
    void shouldProcessValidMessage() throws Exception {
        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(validEvent);
        doNothing().when(inboxEventService).processContractorEvent(validEvent);
        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);
        verify(objectMapper).readValue(any(String.class), eq(ContractorEventDto.class));
        verify(inboxEventService).processContractorEvent(validEvent);
        verify(channel).basicAck(deliveryTag, false);
        verify(channel, never()).basicReject(anyLong(), anyBoolean());
    }

    @Test
    void shouldRejectInvalidJson() throws Exception {
        String invalidJson = "invalid json";
        Message invalidMessage = new Message(invalidJson.getBytes(), new MessageProperties());
        
        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenThrow(new RuntimeException("Invalid JSON"));

        contractorMessageListener.handleContractorEvent(invalidMessage, channel, deliveryTag);

        verify(channel).basicReject(deliveryTag, false);
        verify(channel, never()).basicAck(anyLong(), anyBoolean());
        verify(inboxEventService, never()).processContractorEvent(any());
    }

    @Test
    void shouldRejectMessageWithInvalidData() throws Exception {
        ContractorEventDto invalidEvent = new ContractorEventDto();

        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(invalidEvent);
        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);

        verify(channel).basicReject(deliveryTag, false);
        verify(inboxEventService, never()).processContractorEvent(any());
    }

    @Test
    void shouldRejectOnProcessingException() throws Exception {
        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(validEvent);
        doThrow(new RuntimeException("Processing failed"))
            .when(inboxEventService).processContractorEvent(validEvent);

        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);

        verify(channel).basicReject(deliveryTag, false);
        verify(inboxEventService).processContractorEvent(validEvent);
    }

    @Test
    void shouldHandleChannelException() throws Exception {
        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(validEvent);
        doNothing().when(inboxEventService).processContractorEvent(validEvent);
        doThrow(new IOException("Channel error"))
            .when(channel).basicAck(deliveryTag, false);

        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);
    }

    @Test
    void shouldProcessEventWithAllRequiredFields() throws Exception {
        ContractorDto fullContractorData = new ContractorDto();
        fullContractorData.setId("CNT67890");
        fullContractorData.setName("Full Test Contractor");
        fullContractorData.setNameFull("Full Test Contractor LLC");
        fullContractorData.setInn("7701234567");
        fullContractorData.setOgrn("1027700132195");

        ContractorEventDto fullEvent = new ContractorEventDto();
        fullEvent.setMessageId("msg-67890");
        fullEvent.setContractorId("CNT67890");
        fullEvent.setVersion(2L);
        fullEvent.setEventType("CONTRACTOR_UPDATED");
        fullEvent.setEventTime(LocalDateTime.now());
        fullEvent.setContractorData(fullContractorData);

        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(fullEvent);
        doNothing().when(inboxEventService).processContractorEvent(fullEvent);

        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);

        verify(inboxEventService).processContractorEvent(fullEvent);
        verify(channel).basicAck(deliveryTag, false);
    }

    @Test
    void shouldHandleMessageWithAutoGeneratedMessageId() throws Exception {
        ContractorEventDto eventWithoutMessageId = new ContractorEventDto();
        eventWithoutMessageId.setContractorId("CNT99999");
        eventWithoutMessageId.setVersion(1L);
        eventWithoutMessageId.setEventType("contractor.created");
        eventWithoutMessageId.setEventTime(LocalDateTime.now());

        ContractorDto contractorData = new ContractorDto();
        contractorData.setId("CNT99999");
        contractorData.setName("New Contractor");
        eventWithoutMessageId.setContractorData(contractorData);

        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(eventWithoutMessageId);
        doNothing().when(inboxEventService).processContractorEvent(eventWithoutMessageId);

        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);

        verify(inboxEventService).processContractorEvent(eventWithoutMessageId);
        verify(channel).basicAck(deliveryTag, false);
    }

    @Test
    void shouldNormalizeEventType() throws Exception {
        ContractorEventDto eventWithNonStandardType = new ContractorEventDto();
        eventWithNonStandardType.setMessageId("msg-normalize");
        eventWithNonStandardType.setContractorId("CNT11111");
        eventWithNonStandardType.setVersion(1L);
        eventWithNonStandardType.setEventType("updated"); // Будет нормализован
        eventWithNonStandardType.setEventTime(LocalDateTime.now());
        eventWithNonStandardType.setContractorData(validEvent.getContractorData());

        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(eventWithNonStandardType);
        doNothing().when(inboxEventService).processContractorEvent(any(ContractorEventDto.class));

        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);

        verify(inboxEventService).processContractorEvent(argThat(event ->
            "CONTRACTOR_UPDATED".equals(event.getEventType())
        ));
        verify(channel).basicAck(deliveryTag, false);
    }

    @Test
    void shouldValidateContractorData() throws Exception {
        ContractorDto invalidContractorData = new ContractorDto();

        ContractorEventDto eventWithInvalidContractor = new ContractorEventDto();
        eventWithInvalidContractor.setMessageId("msg-invalid-contractor");
        eventWithInvalidContractor.setContractorId("CNT22222");
        eventWithInvalidContractor.setVersion(1L);
        eventWithInvalidContractor.setEventType("contractor.updated");
        eventWithInvalidContractor.setEventTime(LocalDateTime.now());
        eventWithInvalidContractor.setContractorData(invalidContractorData);

        when(objectMapper.readValue(any(String.class), eq(ContractorEventDto.class)))
            .thenReturn(eventWithInvalidContractor);

        contractorMessageListener.handleContractorEvent(validMessage, channel, deliveryTag);

        verify(channel).basicReject(deliveryTag, false);
        verify(inboxEventService, never()).processContractorEvent(any());
    }
}