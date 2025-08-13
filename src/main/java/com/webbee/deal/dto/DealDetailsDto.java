package com.webbee.deal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO с подробной информацией о сделке.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealDetailsDto {

    /**
     * Уникальный Id сделки.
     */
    @Schema(description = "Уникальный Id сделки", example = "c60c4b5a-6c4a-4b88-b6fa-ff54f398a5ad")
    private UUID id;
    /**
     * Описание сделки.
     */
    @Schema(description = "Описание сделки", example = "Поставка оборудования")
    private String description;
    /**
     * Номер договора.
     */
    @Schema(description = "Номер договора", example = "001")
    private String agreementNumber;
    /**
     * Дата заключения договора.
     */
    @Schema(description = "Дата заключения договора", example = "2024-06-01")
    private LocalDate agreementDate;
    /**
     * Дата начала действия договора.
     */
    @Schema(description = "Дата начала действия договора", example = "2024-06-01T10:00:00")
    private LocalDateTime agreementStartDt;
    /**
     * Дата, с которой доступна сделка.
     */
    @Schema(description = "Дата доступности сделки", example = "2024-06-05")
    private LocalDate availabilityDate;
    /**
     * Тип сделки (краткая информация).
     */
    @Schema(description = "Тип сделки")
    private DealTypeShortDto type;
    /**
     * Статус сделки (краткая информация).
     */
    @Schema(description = "Статус сделки")
    private DealStatusShortDto status;
    /**
     * Сумма сделки (краткая информация).
     */
    @Schema(description = "Сумма сделки")
    private DealSumShortDto sum;
    /**
     * Дата закрытия сделки.
     */
    @Schema(description = "Дата закрытия сделки", example = "2024-12-31T18:00:00")
    private LocalDateTime closeDt;
    /**
     * Список контрагентов, участвующих в сделке (краткая информация).
     */
    @Schema(description = "Список контрагентов в сделке")
    private List<DealContractorShortDto> contractors;

}
