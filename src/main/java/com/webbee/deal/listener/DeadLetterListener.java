package com.webbee.deal.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.config.RabbitMQConfig;
import com.webbee.deal.dto.ContractorEventDto;
import com.webbee.deal.dto.ContractorUpdateMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Слушатель Dead Letter Queue для анализа проблемных сообщений.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeadLetterListener {

    private final ObjectMapper objectMapper;

    @RabbitListener(
        queues = RabbitMQConfig.DEALS_CONTRACTOR_DEAD_QUEUE,
        containerFactory = "autoConvertListenerContainerFactory"
    )
    public void handleDeadLetter(Message message) {
        log.warn("ПОЛУЧЕНО МЕРТВОЕ СООБЩЕНИЕ");
        log.warn("Queue: {}", RabbitMQConfig.DEALS_CONTRACTOR_DEAD_QUEUE);
        String messageBody = new String(message.getBody());
        log.warn("Размер сообщения: {} байт", message.getBody().length);
        log.warn("Содержимое: {}", messageBody);
        var properties = message.getMessageProperties();
        log.warn("Headers: {}", properties.getHeaders());
        log.warn("Routing Key: {}", properties.getReceivedRoutingKey());
        log.warn("Exchange: {}", properties.getReceivedExchange());
        log.warn("Content Type: {}", properties.getContentType());
        log.warn("Content Encoding: {}", properties.getContentEncoding());
        if (properties.getHeaders().containsKey("x-death")) {
            log.warn("Информация о смерти сообщения: {}",
                    properties.getHeaders().get("x-death"));
        }
        tryDeserializeMessage(messageBody);
        log.warn("КОНЕЦ МЕРТВОГО СООБЩЕНИЯ");
    }

    /**
     * Пытается десериализовать сообщение в различные форматы.
     */
    private void tryDeserializeMessage(String messageBody) {
        try {
            ContractorEventDto eventDto = objectMapper.readValue(messageBody, ContractorEventDto.class);
            log.warn("Успешно десериализовано как ContractorEventDto:");
            log.warn("MessageId: {}", eventDto.getMessageId());
            log.warn("ContractorId: {}", eventDto.getContractorId());
            log.warn("Version: {}", eventDto.getVersion());
            log.warn("EventType: {}", eventDto.getEventType());
            log.warn("EventTime: {}", eventDto.getEventTime());
            if (eventDto.getContractorData() != null) {
                log.warn("ContractorData.Id: {}", eventDto.getContractorData().getId());
                log.warn("ContractorData.Name: {}", eventDto.getContractorData().getName());
                log.warn("ContractorData.IsActive: {}", eventDto.getContractorData().getIsActive());
            }
            return;
        } catch (Exception e) {
            log.warn("Не удалось десериализовать как ContractorEventDto: {}", e.getMessage());
        }
        try {
            ContractorUpdateMessageDto updateDto = objectMapper.readValue(messageBody, ContractorUpdateMessageDto.class);
            log.warn("Успешно десериализовано как ContractorUpdateMessageDto:");
            log.warn("ContractorId: {}", updateDto.getContractorId());
            log.warn("Name: {}", updateDto.getName());
            log.warn("Inn: {}", updateDto.getInn());
            return;
        } catch (Exception e) {
            log.warn("Не удалось десериализовать как ContractorUpdateMessageDto: {}", e.getMessage());
        }
        try {
            var jsonNode = objectMapper.readTree(messageBody);
            log.warn("📋 JSON содержит следующие поля:");
            jsonNode.fieldNames().forEachRemaining(field -> {
                var value = jsonNode.get(field);
                String preview = value.isTextual() ? value.asText() : value.toString();
                if (preview.length() > 50) {
                    preview = preview.substring(0, 50) + "...";
                }
                log.warn(" {} ({}): {}", field, value.getNodeType(), preview);
            });
        } catch (Exception e) {
            log.warn(" JSON не является валидным: {}", e.getMessage());
        }
    }

}
