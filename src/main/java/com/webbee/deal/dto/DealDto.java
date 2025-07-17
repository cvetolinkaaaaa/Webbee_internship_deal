package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для основной информации о сделке.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealDto {

    /**
     * Уникальный Id сделки.
     */
    @Schema(description = "Уникальный Id сделки", example = "c60c4b5a-6c4a-4b88-b6fa-ff54f398a5ad")
    @JsonProperty("id")
    private UUID id;
    /**
     * Описание сделки.
     */
    @Schema(description = "Описание сделки", example = "Поставка оборудования")
    @JsonProperty("description")
    private String description;
    /**
     * Номер договора.
     */
    @Schema(description = "Номер договора", example = "001")
    @JsonProperty("agreementNumber")
    private String agreementNumber;
    /**
     * Дата заключения договора.
     */
    @Schema(description = "Дата заключения договора", example = "2024-06-01")
    @JsonProperty("agreementDate")
    private LocalDate agreementDate;
    /**
     * Дата начала действия договора.
     */
    @Schema(description = "Дата начала действия договора", example = "2024-06-01T10:00:00")
    @JsonProperty("agreementStartDt")
    private LocalDateTime agreementStartDt;
    /**
     * Дата, с которой сделка становится доступной.
     */
    @Schema(description = "Дата доступности сделки", example = "2024-06-05")
    @JsonProperty("availabilityDate")
    private LocalDate availabilityDate;
    /**
     * Тип сделки.
     */
    @Schema(description = "Тип сделки")
    @JsonProperty("type")
    private DealTypeDto type;
    /**
     * Статус сделки.
     */
    @Schema(description = "Статус сделки")
    @JsonProperty("status")
    private DealStatusDto status;
    /**
     * Дата закрытия сделки.
     */
    @Schema(description = "Дата закрытия сделки", example = "2024-12-31T18:00:00")
    @JsonProperty("closeDt")
    private LocalDateTime closeDt;
    /**
     * Дата создания сделки.
     */
    @Schema(description = "Дата создания сделки", example = "2024-06-01T10:05:00")
    @JsonProperty("createDate")
    private LocalDateTime createDate;
    /**
     * Дата последнего изменения сделки.
     */
    @Schema(description = "Дата последнего изменения сделки", example = "2024-06-10T15:20:00")
    @JsonProperty("imodifyDated")
    private LocalDateTime modifyDate;
    /**
     * Id пользователя, создавшего сделку.
     */
    @Schema(description = "Id пользователя, создавшего сделку", example = "user1")
    @JsonProperty("createUserId")
    private String createUserId;
    /**
     * Id пользователя, изменившего сделку.
     */
    @Schema(description = "Id пользователя, изменившего сделку", example = "user2")
    @JsonProperty("modifyUserId")
    private String modifyUserId;
    /**
     * Признак активности сделки.
     */
    @Schema(description = "Признак активности сделки", example = "true")
    @JsonProperty("isActive")
    private Boolean isActive;

}
