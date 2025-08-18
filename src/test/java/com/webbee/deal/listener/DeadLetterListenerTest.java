package com.webbee.deal.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.dto.ContractorEventDto;
import com.webbee.deal.dto.ContractorUpdateMessageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeadLetterListenerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Message message;

    @Mock
    private MessageProperties messageProperties;

    @InjectMocks
    private DeadLetterListener deadLetterListener;

    @Test
    void handleDeadLetter_Success() throws Exception {
        String messageBody = "{\"messageId\":\"test-id\",\"contractorId\":\"123\"}";
        byte[] bodyBytes = messageBody.getBytes();
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-death", "some death info");

        when(message.getBody()).thenReturn(bodyBytes);
        when(message.getMessageProperties()).thenReturn(messageProperties);
        when(messageProperties.getHeaders()).thenReturn(headers);
        when(messageProperties.getReceivedRoutingKey()).thenReturn("contractor.update");
        when(messageProperties.getReceivedExchange()).thenReturn("contractors_exchange");
        when(messageProperties.getContentType()).thenReturn("application/json");
        when(messageProperties.getContentEncoding()).thenReturn("UTF-8");

        ContractorEventDto eventDto = new ContractorEventDto();
        eventDto.setMessageId("test-id");
        eventDto.setContractorId("123");

        when(objectMapper.readValue(messageBody, ContractorEventDto.class)).thenReturn(eventDto);

        deadLetterListener.handleDeadLetter(message);

        verify(objectMapper).readValue(messageBody, ContractorEventDto.class);
    }

    @Test
    void handleDeadLetter_FallbackToOldFormat() throws Exception {
        String messageBody = "{\"contractorId\":\"123\",\"name\":\"Test\"}";
        byte[] bodyBytes = messageBody.getBytes();
        Map<String, Object> headers = new HashMap<>();

        when(message.getBody()).thenReturn(bodyBytes);
        when(message.getMessageProperties()).thenReturn(messageProperties);
        when(messageProperties.getHeaders()).thenReturn(headers);
        when(messageProperties.getReceivedRoutingKey()).thenReturn("contractor.update");
        when(messageProperties.getReceivedExchange()).thenReturn("contractors_exchange");

        when(objectMapper.readValue(messageBody, ContractorEventDto.class))
                .thenThrow(new RuntimeException("Cannot deserialize"));

        ContractorUpdateMessageDto updateDto = new ContractorUpdateMessageDto();
        updateDto.setContractorId("123");
        updateDto.setName("Test");

        when(objectMapper.readValue(messageBody, ContractorUpdateMessageDto.class)).thenReturn(updateDto);

        deadLetterListener.handleDeadLetter(message);

        verify(objectMapper).readValue(messageBody, ContractorEventDto.class);
        verify(objectMapper).readValue(messageBody, ContractorUpdateMessageDto.class);
    }

    @Test
    void handleDeadLetter_InvalidJson() throws Exception {
        String messageBody = "invalid json";
        byte[] bodyBytes = messageBody.getBytes();
        Map<String, Object> headers = new HashMap<>();

        when(message.getBody()).thenReturn(bodyBytes);
        when(message.getMessageProperties()).thenReturn(messageProperties);
        when(messageProperties.getHeaders()).thenReturn(headers);

        when(objectMapper.readValue(messageBody, ContractorEventDto.class))
                .thenThrow(new RuntimeException("Cannot deserialize"));
        when(objectMapper.readValue(messageBody, ContractorUpdateMessageDto.class))
                .thenThrow(new RuntimeException("Cannot deserialize"));
        when(objectMapper.readTree(messageBody))
                .thenThrow(new RuntimeException("Invalid JSON"));

        deadLetterListener.handleDeadLetter(message);

        verify(objectMapper).readValue(messageBody, ContractorEventDto.class);
        verify(objectMapper).readValue(messageBody, ContractorUpdateMessageDto.class);
        verify(objectMapper).readTree(messageBody);
    }
}