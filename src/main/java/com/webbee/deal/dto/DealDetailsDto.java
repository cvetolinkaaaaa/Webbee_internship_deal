package com.webbee.deal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class DealDetailsDto {
    private UUID id;
    private String description;
    private String agreementNumber;
    private LocalDate agreementDate;
    private LocalDateTime agreementStartDt;
    private LocalDate availabilityDate;
    private DealTypeShortDto type;
    private DealStatusShortDto status;
    private DealSumShortDto sum;
    private LocalDateTime closeDt;
    private List<DealContractorShortDto> contractors;

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

    public DealTypeShortDto getType() {
        return type;
    }

    public void setType(DealTypeShortDto type) {
        this.type = type;
    }

    public DealStatusShortDto getStatus() {
        return status;
    }

    public void setStatus(DealStatusShortDto status) {
        this.status = status;
    }

    public DealSumShortDto getSum() {
        return sum;
    }

    public void setSum(DealSumShortDto sum) {
        this.sum = sum;
    }

    public LocalDateTime getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(LocalDateTime closeDt) {
        this.closeDt = closeDt;
    }

    public List<DealContractorShortDto> getContractors() {
        return contractors;
    }

    public void setContractors(List<DealContractorShortDto> contractors) {
        this.contractors = contractors;
    }
}
