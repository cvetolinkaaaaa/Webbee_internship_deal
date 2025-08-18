package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Модель для хранения входящих событий (Inbox Pattern).
 * Ретраи обрабатываются на уровне RabbitMQ.
 */
@Entity
@Table(name = "inbox_event")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InboxEvent {

    @Id
    private String id;

    /** Уникальный идентификатор сообщения от внешнего сервиса */
    @Column(name = "message_id", unique = true, nullable = false)
    private String messageId;

    /** Идентификатор агрегата (contractor_id) */
    @Column(name = "aggregate_id", nullable = false)
    private String aggregateId;

    /** Тип агрегата */
    @Column(name = "aggregate_type", nullable = false)
    private String aggregateType;

    /** Тип события */
    @Column(name = "event_type", nullable = false)
    private String eventType;

    /** Версия агрегата на момент события */
    @Column(name = "version", nullable = false)
    private Long version;

    /** Данные события в JSON */
    @Column(name = "event_data", columnDefinition = "TEXT", nullable = false)
    private String eventData;

    /** Временная метка создания события на стороне отправителя */
    @Column(name = "event_timestamp", nullable = false)
    private LocalDateTime eventTimestamp;

    /** Дата получения события нашим сервисом */
    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    /** Дата успешной обработки */
    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    /** Статус обработки */
    @Column(name = "status", nullable = false)
    private String status;

    /** Причина игнорирования (для устаревших сообщений) */
    @Column(name = "ignore_reason")
    private String ignoreReason;

    public static final String STATUS_RECEIVED = "RECEIVED";
    public static final String STATUS_PROCESSING = "PROCESSING";
    public static final String STATUS_PROCESSED = "PROCESSED";
    public static final String STATUS_IGNORED = "IGNORED";
    public static final String STATUS_FAILED = "FAILED";
    public static final String AGGREGATE_TYPE_CONTRACTOR = "CONTRACTOR";
    public static final String EVENT_TYPE_CONTRACTOR_CREATED = "CONTRACTOR_CREATED";
    public static final String EVENT_TYPE_CONTRACTOR_UPDATED = "CONTRACTOR_UPDATED";
    public static final String EVENT_TYPE_CONTRACTOR_DELETED = "CONTRACTOR_DELETED";

    public boolean isReceived() {
        return STATUS_RECEIVED.equals(status);
    }

    public boolean isProcessing() {
        return STATUS_PROCESSING.equals(status);
    }

    public boolean isProcessed() {
        return STATUS_PROCESSED.equals(status);
    }

    public boolean isIgnored() {
        return STATUS_IGNORED.equals(status);
    }

    public boolean isFailed() {
        return STATUS_FAILED.equals(status);
    }

    public void setStatusProcessed() {
        this.status = STATUS_PROCESSED;
        this.processedAt = LocalDateTime.now();
    }

    public void setStatusIgnored(String reason) {
        this.status = STATUS_IGNORED;
        this.ignoreReason = reason;
        this.processedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (receivedAt == null) {
            receivedAt = LocalDateTime.now();
        }
        if (status == null) {
            status = STATUS_RECEIVED;
        }
    }

    public static class InboxEventBuilder {
        public InboxEvent build() {
            if (this.id == null) {
                this.id = UUID.randomUUID().toString();
            }
            if (this.receivedAt == null) {
                this.receivedAt = LocalDateTime.now();
            }
            if (this.status == null) {
                this.status = STATUS_RECEIVED;
            }
            return new InboxEvent(id, messageId, aggregateId, aggregateType, eventType,
                    version, eventData, eventTimestamp, receivedAt, processedAt,
                    status, ignoreReason);
        }
    }

}
