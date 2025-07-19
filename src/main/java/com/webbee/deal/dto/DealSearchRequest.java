package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO-запрос для поиска сделок по различным параметрам.
 */
@Getter
@Setter
public class DealSearchRequest {

    /**
     * Идентификатор сделки.
     */
    @Schema(description = "ID сделки", example = "e8ff7e4e-3a21-4c6e-b5cd-2a03fd8e5bb7")
    private UUID dealId;
    /**
     * Описание сделки (поиск по подстроке).
     */
    @Schema(description = "Описание сделки для поиска", example = "Аренда")
    private String description;
    /**
     * Номер договора.
     */
    @Schema(description = "Номер договора", example = "001")
    private String agreementNumber;
    /**
     * Дата заключения договора: с.
     */
    @Schema(description = "Дата заключения договора (от)", example = "2024-01-01")
    private LocalDate agreementDateFrom;
    /**
     * Дата заключения договора: по.
     */
    @Schema(description = "Дата заключения договора (до)", example = "2024-12-31")
    private LocalDate agreementDateTo;
    /**
     * Дата доступности: с.
     */
    @Schema(description = "Дата доступности сделки (от)", example = "2024-01-01")
    private LocalDate availabilityDateFrom;
    /**
     * Дата доступности: по.
     */
    @Schema(description = "Дата доступности сделки (до)", example = "2024-12-31")
    private LocalDate availabilityDateTo;
    /**
     * Список id типов сделки для фильтрации.
     */
    @Schema(description = "ID типов сделки")
    private List<String> typeIds;
    /**
     * Список id статусов сделки для фильтрации.
     */
    @Schema(description = "ID статусов сделки")
    private List<String> statusIds;
    /**
     * Дата закрытия: с.
     */
    @Schema(description = "Дата закрытия сделки (от)", example = "2024-01-01")
    private LocalDate closeDtFrom;
    /**
     * Дата закрытия: по.
     */
    @Schema(description = "Дата закрытия сделки (до)", example = "2024-12-31")
    private LocalDate closeDtTo;
    /**
     * Поиск по заёмщику.
     */
    @Schema(description = "Поиск по заёмщику", example = "ООО Пример")
    private String borrowerSearch;
    /**
     * Поиск по поручителю.
     */
    @Schema(description = "Поиск по поручителю", example = "АО Пример")
    private String warrantySearch;
    /**
     * Значение суммы сделки (поиск по сумме).
     */
    @Schema(description = "Сумма сделки для поиска", example = "1000000")
    private String sumValue;
    /**
     * Валюта суммы сделки.
     */
    @Schema(description = "Валюта суммы сделки", example = "RUB")
    private String sumCurrency;
    /**
     * Номер страницы.
     */
    @Schema(description = "Номер страницы (начинается с 0)", example = "0", defaultValue = "0")
    private Integer page = 0;
    /**
     * Размер страницы (количество элементов на странице).
     */
    @Schema(description = "Размер страницы (по умолчанию 20)", example = "20", defaultValue = "20")
    private Integer size = 20;
    /**
     * Поле для сортировки.
     */
    @Schema(description = "Поле для сортировки", example = "agreementDate", defaultValue = "agreementDate")
    private String sortField = "agreementDate";
    /**
     * Направление сортировки (ASC/DESC).
     */
    @Schema(description = "Направление сортировки", example = "DESC", defaultValue = "DESC")
    private String sortDir = "DESC";

}
