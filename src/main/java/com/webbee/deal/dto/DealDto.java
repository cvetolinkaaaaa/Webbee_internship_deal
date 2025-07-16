package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class DealDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("agreementNumber")
    private String agreementNumber;
    @JsonProperty("agreementDate")
    private LocalDate agreementDate;
    @JsonProperty("agreementStartDt")
    private LocalDateTime agreementStartDt;
    @JsonProperty("availabilityDate")
    private LocalDate availabilityDate;
    @JsonProperty("type")
    private DealTypeDto type;
    @JsonProperty("status")
    private DealStatusDto status;
    @JsonProperty("closeDt")
    private LocalDateTime closeDt;
    @JsonProperty("createDate")
    private LocalDateTime createDate;
    @JsonProperty("imodifyDated")
    private LocalDateTime modifyDate;
    @JsonProperty("createUserId")
    private String createUserId;
    @JsonProperty("modifyUserId")
    private String modifyUserId;
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
