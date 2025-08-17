package com.webbee.deal.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.config.RabbitMQConfig;
import com.webbee.deal.dto.ContractorUpdateMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeadLetterListener {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.DEALS_CONTRACTOR_DEAD_QUEUE)
    public void handleDeadLetter(Message message) {
        log.warn("🚨 === ПОЛУЧЕНО МЕРТВОЕ СООБЩЕНИЕ === 🚨");
        log.warn("Queue: {}", RabbitMQConfig.DEALS_CONTRACTOR_DEAD_QUEUE);
        
        String messageBody = new String(message.getBody());
        log.warn("Содержимое: {}", messageBody);
        log.warn("Headers: {}", message.getMessageProperties().getHeaders());
        log.warn("Routing Key: {}", message.getMessageProperties().getReceivedRoutingKey());
        log.warn("Exchange: {}", message.getMessageProperties().getReceivedExchange());
        log.warn("Content Type: {}", message.getMessageProperties().getContentType());
        
        // Пытаемся понять, что это за сообщение
        try {
            ContractorUpdateMessageDto dto = objectMapper.readValue(messageBody, ContractorUpdateMessageDto.class);
            log.warn("Десериализованное DTO: {}", dto);
        } catch (Exception e) {
            log.warn("Не удалось десериализовать мертвое сообщение: {}", e.getMessage());
        }
        
        // Дополнительная информация о причине попадания в DLQ
        if (message.getMessageProperties().getHeaders().containsKey("x-death")) {
            log.warn("Информация о смерти сообщения: {}", 
                    message.getMessageProperties().getHeaders().get("x-death"));
        }
        
        log.warn("=== КОНЕЦ МЕРТВОГО СООБЩЕНИЯ ===");
    }
}