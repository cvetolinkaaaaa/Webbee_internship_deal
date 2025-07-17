package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO-запрос для поиска сделок по различным параметрам.
 */
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

    public UUID getDealId() {
        return dealId;
    }

    public void setDealId(UUID dealId) {
        this.dealId = dealId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public LocalDate getAgreementDateFrom() {
        return agreementDateFrom;
    }

    public void setAgreementDateFrom(LocalDate agreementDateFrom) {
        this.agreementDateFrom = agreementDateFrom;
    }

    public LocalDate getAgreementDateTo() {
        return agreementDateTo;
    }

    public void setAgreementDateTo(LocalDate agreementDateTo) {
        this.agreementDateTo = agreementDateTo;
    }

    public LocalDate getAvailabilityDateFrom() {
        return availabilityDateFrom;
    }

    public void setAvailabilityDateFrom(LocalDate availabilityDateFrom) {
        this.availabilityDateFrom = availabilityDateFrom;
    }

    public LocalDate getAvailabilityDateTo() {
        return availabilityDateTo;
    }

    public void setAvailabilityDateTo(LocalDate availabilityDateTo) {
        this.availabilityDateTo = availabilityDateTo;
    }

    public List<String> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(List<String> typeIds) {
        this.typeIds = typeIds;
    }

    public List<String> getStatusIds() {
        return statusIds;
    }

    public void setStatusIds(List<String> statusIds) {
        this.statusIds = statusIds;
    }

    public LocalDate getCloseDtFrom() {
        return closeDtFrom;
    }

    public void setCloseDtFrom(LocalDate closeDtFrom) {
        this.closeDtFrom = closeDtFrom;
    }

    public LocalDate getCloseDtTo() {
        return closeDtTo;
    }

    public void setCloseDtTo(LocalDate closeDtTo) {
        this.closeDtTo = closeDtTo;
    }

    public String getBorrowerSearch() {
        return borrowerSearch;
    }

    public void setBorrowerSearch(String borrowerSearch) {
        this.borrowerSearch = borrowerSearch;
    }

    public String getWarrantySearch() {
        return warrantySearch;
    }

    public void setWarrantySearch(String warrantySearch) {
        this.warrantySearch = warrantySearch;
    }

    public String getSumValue() {
        return sumValue;
    }

    public void setSumValue(String sumValue) {
        this.sumValue = sumValue;
    }

    public String getSumCurrency() {
        return sumCurrency;
    }

    public void setSumCurrency(String sumCurrency) {
        this.sumCurrency = sumCurrency;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

}
