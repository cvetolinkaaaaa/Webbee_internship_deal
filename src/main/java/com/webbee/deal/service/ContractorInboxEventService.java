
package com.webbee.deal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.audit_lib.starter.core.AuditLog;
import com.webbee.deal.dto.ContractorEventDto;
import com.webbee.deal.entity.InboxEvent;
import com.webbee.deal.repository.InboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с Inbox паттерном для событий контрагентов.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContractorInboxEventService {

    private final InboxEventRepository inboxEventRepository;
    private final ContractorUpdateService contractorUpdateService;
    private final ObjectMapper objectMapper;

    /**
     * Обрабатывает входящее событие с применением Inbox паттерна и версионирования.
     */
    @AuditLog(logLevel = AuditLog.LogLevel.INFO)
    @Transactional
    public void processContractorEvent(ContractorEventDto event) {
        log.info("Получено событие для обработки: messageId={}, contractorId={}, version={}, eventType={}",
                 event.getMessageId(), event.getContractorId(), event.getVersion(), event.getEventType());
        if (inboxEventRepository.existsByMessageId(event.getMessageId())) {
            log.info("Событие {} уже было обработано ранее - пропускаем", event.getMessageId());
            return;
        }
        InboxEvent inboxEvent = createInboxEvent(event);
        if (isEventOutdated(event)) {
            log.warn("Событие {} устарело - найдена более новая версия для контрагента {}. " +
                    "Версия события: {}",
                    event.getMessageId(), event.getContractorId(), event.getVersion());
            inboxEvent.setStatusIgnored("Найдена более новая версия данных (версия " + event.getVersion() + ")");
            inboxEventRepository.save(inboxEvent);
            return;
        }
        inboxEvent.setStatus("PROCESSING");
        inboxEventRepository.save(inboxEvent);
        try {
            log.info("Обработка события {}: контрагент={}, версия={}, тип={}",
                        event.getMessageId(), event.getContractorId(), event.getVersion(), event.getEventType());
            processEventByType(event);
            inboxEvent.setStatusProcessed();
            inboxEventRepository.save(inboxEvent);
            log.info("Событие {} успешно обработано", event.getMessageId());
        } catch (Exception e) {
            log.error("Ошибка при обработке события {}: {}", event.getMessageId(), e.getMessage(), e);
            inboxEvent.setStatus("FAILED");
            inboxEvent.setIgnoreReason("Ошибка обработки: " + e.getMessage());
            inboxEvent.setProcessedAt(LocalDateTime.now());
            try {
                inboxEventRepository.save(inboxEvent);
                log.warn("Событие {} помечено как неуспешное в Inbox", event.getMessageId());
            } catch (Exception saveEx) {
                log.error("Не удалось сохранить статус ошибки для события {}: {}",
                         event.getMessageId(), saveEx.getMessage(), saveEx);
            }
            throw e;
        }
    }

    /**
     * Обрабатывает событие в зависимости от его типа.
     */
    private void processEventByType(ContractorEventDto event) {
        String eventType = event.getEventType().toUpperCase();

        log.info("Обработка события типа: {} для контрагента {}", eventType, event.getContractorId());

        switch (eventType) {
            case "CONTRACTOR_CREATED":
                log.info("Создание нового контрагента: {}", event.getContractorId());
                contractorUpdateService.createContractor(event.getContractorData());
                break;
            case "CONTRACTOR_UPDATED":
                log.info("Обновление контрагента: {}", event.getContractorId());
                contractorUpdateService.updateContractor(event.getContractorData());
                break;
            default:
                log.warn("Неизвестный тип события: {}, обрабатываем как обновление", eventType);
                if (event.getContractorData() != null) {
                    contractorUpdateService.updateContractor(event.getContractorData());
                } else {
                    log.error("Не можем обработать событие {} - отсутствуют данные контрагента", eventType);
                    throw new IllegalArgumentException("Отсутствуют данные контрагента для события: " + eventType);
                }
        }

        log.info("Событие типа {} успешно обработано для контрагента {}", eventType, event.getContractorId());
    }

    /**
    * Создает запись inbox события из события.
    */
    private InboxEvent createInboxEvent(ContractorEventDto event) {
        try {
            return InboxEvent.builder()
                    .messageId(event.getMessageId())
                    .aggregateId(event.getContractorId())
                    .aggregateType(InboxEvent.AGGREGATE_TYPE_CONTRACTOR)
                    .eventType(event.getEventType())
                    .version(event.getVersion())
                    .eventData(objectMapper.writeValueAsString(event))
                    .eventTimestamp(event.getEventTime())
                    .status("RECEIVED")
                    .build();
        } catch (Exception e) {
            log.error("Ошибка при создании inbox события: {}", event.getMessageId(), e);
            throw new RuntimeException("Ошибка при создании inbox события: " + event.getMessageId(), e);
        }
    }

    /**
    * Проверяет, устарело ли событие на основе версионирования.
    */
    private boolean isEventOutdated(ContractorEventDto event) {
        List<InboxEvent> lastProcessedEvents =
            inboxEventRepository.findLastProcessedEventByAggregate(
                event.getContractorId(),
                InboxEvent.AGGREGATE_TYPE_CONTRACTOR,
                InboxEvent.STATUS_PROCESSED);

        if (lastProcessedEvents.isEmpty()) {
            log.debug("Событие для контрагента {} не найдено в истории - обрабатываем как новое",
                    event.getContractorId());
            return false;
        }

        InboxEvent lastProcessed = lastProcessedEvents.get(0);
        boolean isOutdated = event.getVersion() <= lastProcessed.getVersion();
        if (isOutdated) {
            log.debug("Событие устарело: текущая версия={}, последняя обработанная версия={}",
                     event.getVersion(), lastProcessed.getVersion());
        } else {
            log.debug("Событие актуально: текущая версия={}, последняя обработанная версия={}",
                     event.getVersion(), lastProcessed.getVersion());
        }

        return isOutdated;
    }

    /**
    * Возвращает историю событий для контрагента.
     */
    public List<InboxEvent> getEventHistory(String contractorId) {
        return inboxEventRepository.findAllByAggregateOrderByVersionDesc(
            contractorId, InboxEvent.AGGREGATE_TYPE_CONTRACTOR);
    }

    /**
    * Возвращает статистику обработанных событий.
     */
    public Long getProcessedEventsCount(LocalDateTime since) {
        return inboxEventRepository.countProcessedEventsSince(InboxEvent.STATUS_PROCESSED, since);
    }

    /**
    * Возвращает количество событий по статусам.
     */
    public long getEventsCountByStatus(String status) {
        return inboxEventRepository.countByStatus(status);
    }

    /**
    * Возвращает все необработанные события (для диагностики).
     */
    public List<InboxEvent> getPendingEvents() {
        return inboxEventRepository.findByStatusIn(List.of("RECEIVED", "PROCESSING"));
    }

    /**
    * Возвращает все события с ошибками (для диагностики).
     */
    public List<InboxEvent> getFailedEvents() {
        return inboxEventRepository.findByStatus("FAILED");
    }

}
