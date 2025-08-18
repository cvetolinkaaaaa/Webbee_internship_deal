package com.webbee.deal.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.webbee.deal.config.RabbitMQConfig;
import com.webbee.deal.dto.ContractorEventDto;
import com.webbee.deal.service.ContractorInboxEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


/**
 * Слушатель сообщений о контрагентах с поддержкой Inbox Event паттерна.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ContractorMessageListener {

    private final ContractorInboxEventService inboxEventService;
    private final ObjectMapper objectMapper;

    /**
     * Обрабатывает события контрагентов через Inbox Event паттерн.
     * Использует сырые данные Message для полного контроля над десериализацией.
     */
    @RabbitListener(
        queues = RabbitMQConfig.DEALS_CONTRACTOR_QUEUE,
        ackMode = "MANUAL",
        containerFactory = "rabbitListenerContainerFactory"
    )
    public void handleContractorEvent(
            Message message,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag
    ) {

        String bodyString = new String(message.getBody());
        log.info("Получено RabbitMQ сообщение (размер: {} байт)", message.getBody().length);
        log.info("Headers: {}", message.getMessageProperties().getHeaders());
        log.info("Routing Key: {}", message.getMessageProperties().getReceivedRoutingKey());
        log.info("Content: {}", bodyString);

        try {
            ContractorEventDto contractorEvent = deserializeEvent(bodyString);
            normalizeAndValidateEvent(contractorEvent);
            log.info("Передача события в Inbox сервис: messageId={}, contractorId={}, version={}, eventType={}",
                    contractorEvent.getMessageId(), contractorEvent.getContractorId(),
                     contractorEvent.getVersion(), contractorEvent.getEventType());
            inboxEventService.processContractorEvent(contractorEvent);
            channel.basicAck(deliveryTag, false);
            log.info("RabbitMQ событие {} успешно обработано и подтверждено",
                    contractorEvent.getMessageId());
            } catch (Exception e) {
                log.error("Ошибка при обработке RabbitMQ события: {}", bodyString, e);
            try {
                channel.basicReject(deliveryTag, false);
                log.warn("RabbitMQ событие отклонено и отправлено в dead letter exchange");
            } catch (Exception ackException) {
                log.error("Ошибка при отклонении RabbitMQ события", ackException);
            }
        }
    }

    /**
     * Десериализует событие с расширенным логированием ошибок.
     */
    private ContractorEventDto deserializeEvent(String messageBody) {
        try {
            log.debug("Начинаем десериализацию JSON в ContractorEventDto");
            ContractorEventDto event = objectMapper.readValue(messageBody, ContractorEventDto.class);
            log.debug("Десериализация успешна: contractorId={}, version={}, eventType={}",
                     event.getContractorId(), event.getVersion(), event.getEventType());
            return event;
        } catch (Exception e) {
            log.error("Ошибка десериализации JSON в ContractorEventDto:");
            log.error("JSON: {}", messageBody);
            log.error("Тип ошибки: {}", e.getClass().getSimpleName());
            log.error("Сообщение ошибки: {}", e.getMessage());
            analyzeJsonStructure(messageBody);
            throw new RuntimeException("Не удалось десериализовать сообщение: " + e.getMessage(), e);
        }
    }

    /**
    * Анализирует структуру JSON для диагностики проблем.
     */
    private void analyzeJsonStructure(String json) {
        try {
            var jsonNode = objectMapper.readTree(json);
            log.error("JSON содержит следующие поля:");
            jsonNode.fieldNames().forEachRemaining(field -> {
                var value = jsonNode.get(field);
                log.error("  {} ({}): {}", field, value.getNodeType(),
                        value.isTextual() ? value.asText() :
                        (value.toString().length() > 100 ? value.toString().substring(0, 100) + "..." : value.toString()));
            });
        } catch (Exception e) {
            log.error("JSON не является валидным: {}", e.getMessage());
        }
    }

    /**
     * Нормализует и валидирует событие.
     */
    private void normalizeAndValidateEvent(ContractorEventDto event) {
        if (event == null) {
            throw new IllegalArgumentException("Событие не может быть null");
        }
        String messageId = event.getMessageId();
        log.debug("MessageId: {} ({})", messageId,
                 event.getMessageId() == null ? "сгенерирован автоматически" : "получен из сообщения");
        if (event.getEventType() != null) {
            String originalType = event.getEventType();
            String normalizedEventType = normalizeEventType(originalType);
            event.setEventType(normalizedEventType);
            log.debug("Нормализация типа события: '{}' → '{}'", originalType, normalizedEventType);
        }
        validateBasicFields(event);
        if (event.getContractorData() != null) {
            validateContractorData(event.getContractorData());
        } else if (!"CONTRACTOR_DELETED".equals(event.getEventType())) {
            throw new IllegalArgumentException("ContractorData не может быть пустым для события типа: " + event.getEventType());
        }
    }

    /**
     * Валидирует базовые поля события.
     */
    private void validateBasicFields(ContractorEventDto event) {
        if (event.getContractorId() == null || event.getContractorId().trim().isEmpty()) {
            throw new IllegalArgumentException("ContractorId не может быть пустым");
        }

        if (event.getVersion() == null || event.getVersion() <= 0) {
            throw new IllegalArgumentException("Version должна быть положительным числом");
        }

        if (event.getEventType() == null || event.getEventType().trim().isEmpty()) {
            throw new IllegalArgumentException("EventType не может быть пустым");
        }

        if (event.getEventTime() == null) {
            throw new IllegalArgumentException("EventTime не может быть пустым");
        }
    }

    /**
     * Нормализует тип события к стандартным значениям.
     */
    private String normalizeEventType(String eventType) {
        String normalized = eventType.toUpperCase().trim();
        return switch (normalized) {
            case "UPDATED", "UPDATE" -> "CONTRACTOR_UPDATED";
            case "CREATED", "CREATE" -> "CONTRACTOR_CREATED";
            case "DELETED", "DELETE" -> "CONTRACTOR_DELETED";
            default -> {
                if (normalized.startsWith("CONTRACTOR_")) {
                    yield normalized;
                }
                yield "CONTRACTOR_" + normalized;
            }
        };
    }

    /**
     * Валидирует данные контрагента.
     */
    private void validateContractorData(com.webbee.deal.dto.ContractorDto contractorData) {
        if (contractorData.getId() == null || contractorData.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("ContractorData.id не может быть пустым");
        }

        if (contractorData.getName() == null || contractorData.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("ContractorData.name не может быть пустым");
        }

        if (contractorData.getIsActive() == null) {
            log.debug("isActive не установлено, используем значение по умолчанию: true");
            contractorData.setIsActive(true);
        }

        log.debug("Данные контрагента валидны: id={}, name={}, isActive={}",
                 contractorData.getId(), contractorData.getName(), contractorData.getIsActive());
    }

}
