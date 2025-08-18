package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO для событий контрагента из внешнего сервиса.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorEventDto {

    /**
     * Уникальный идентификатор сообщения (может генерироваться автоматически).
     */
    @Schema(description = "Уникальный идентификатор сообщения", example = "msg-12345")
    private String messageId;

    /**
     * Идентификатор контрагента.
     */
    @Schema(description = "Идентификатор контрагента", example = "CNT12345")
    private String contractorId;

    /**
     * Версия данных контрагента (инкрементальная).
     */
    @Schema(description = "Версия данных контрагента", example = "5")
    private Long version;

    /**
     * Тип события.
     */
    @Schema(description = "Тип события", example = "CONTRACTOR_UPDATED")
    private String eventType;

    /**
     * Данные контрагента.
     */
    @Schema(description = "Данные контрагента")
    private ContractorDto contractorData;

    /**
     * Время создания события.
     */
    @Schema(description = "Время создания события", example = "2023-12-01T10:00:00")
    private LocalDateTime eventTime;

    /**
     * Генерирует messageId если он отсутствует.
     */
    public String getMessageId() {
        if (messageId == null || messageId.trim().isEmpty()) {
            messageId = "msg-" + UUID.randomUUID().toString().substring(0, 8) +
                       "-" + contractorId + "-v" + version;
        }
        return messageId;
    }

}
