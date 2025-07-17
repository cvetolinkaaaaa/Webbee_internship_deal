package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO для основной информации о сделке.
 */
@NoArgsConstructor
@AllArgsConstructor
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    public LocalDateTime getAgreementStartDt() {
        return agreementStartDt;
    }

    public void setAgreementStartDt(LocalDateTime agreementStartDt) {
        this.agreementStartDt = agreementStartDt;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public DealTypeDto getType() {
        return type;
    }

    public void setType(DealTypeDto type) {
        this.type = type;
    }

    public DealStatusDto getStatus() {
        return status;
    }

    public void setStatus(DealStatusDto status) {
        this.status = status;
    }

    public LocalDateTime getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(LocalDateTime closeDt) {
        this.closeDt = closeDt;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

}
