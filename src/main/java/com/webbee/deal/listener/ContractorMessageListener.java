package com.webbee.deal.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.webbee.deal.config.RabbitMQConfig;
import com.webbee.deal.dto.ContractorUpdateMessageDto;
import com.webbee.deal.service.ContractorUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * Слушатель сообщений о контрагентах из RabbitMQ.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ContractorMessageListener {

    private final ContractorUpdateService contractorUpdateService;
    private final ObjectMapper objectMapper;

    /**
     * Обрабатывает сообщения об обновлении контрагентов.
     */
    @RabbitListener(queues = RabbitMQConfig.DEALS_CONTRACTOR_QUEUE, ackMode = "MANUAL")
    public void handleContractorUpdate(Message message, 
                                     Channel channel, 
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("=== ПОЛУЧЕНО СЫРОЕ СООБЩЕНИЕ ===");
        log.info("Содержимое: {}", new String(message.getBody()));
        log.info("Content Type: {}", message.getMessageProperties().getContentType());
        log.info("Headers: {}", message.getMessageProperties().getHeaders());
        log.info("Delivery Tag: {}", deliveryTag);

        ContractorUpdateMessageDto contractorUpdate = null;
        
        try {
            // Пытаемся десериализовать сообщение вручную
            String messageBody = new String(message.getBody());
            contractorUpdate = objectMapper.readValue(messageBody, ContractorUpdateMessageDto.class);
            log.info("Успешно десериализовано: {}", contractorUpdate);
            
        } catch (Exception deserializationException) {
            log.error("=== ОШИБКА ДЕСЕРИАЛИЗАЦИИ ===");
            log.error("Не удалось десериализовать сообщение: {}", 
                     new String(message.getBody()), deserializationException);
            
            try {
                channel.basicReject(deliveryTag, false);
                log.info("Сообщение с ошибкой десериализации отклонено и отправлено в dead letter exchange");
                return;
            } catch (Exception e) {
                log.error("Ошибка при отклонении сообщения с ошибкой десериализации", e);
                return;
            }
        }

        // ВРЕМЕННЫЙ КОД ДЛЯ ТЕСТИРОВАНИЯ
        if (true) { // Всегда отправляем в DLQ для теста
            log.error("=== ПРИНУДИТЕЛЬНАЯ ОШИБКА ===");
            try {
                channel.basicReject(deliveryTag, false);
                log.info("Сообщение отклонено и отправлено в dead letter exchange");
                return;
            } catch (Exception e) {
                log.error("Ошибка при отклонении сообщения", e);
                return;
            }
        }

        try {
            contractorUpdateService.updateContractorInfo(contractorUpdate);
            log.info("Сообщение об обновлении контрагента успешно обработано: {}", 
                     contractorUpdate.getContractorId());
            
            channel.basicAck(deliveryTag, false);
            log.info("Сообщение подтверждено (ACK)");
            
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения об обновлении контрагента: {}. " +
                     "Сообщение будет отправлено в dead letter exchange", 
                     contractorUpdate, e);
            
            try {
                channel.basicReject(deliveryTag, false);
                log.info("Сообщение отклонено и отправлено в dead letter exchange: {}", 
                        contractorUpdate != null ? contractorUpdate.getContractorId() : "unknown");
                        
            } catch (Exception ackException) {
                log.error("Ошибка при отклонении сообщения: {}", contractorUpdate, ackException);
            }
        }
    }
}