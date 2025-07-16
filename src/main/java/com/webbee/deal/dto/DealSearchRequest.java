package com.webbee.deal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DealSearchRequest {
    private UUID dealId;
    private String description;
    private String agreementNumber;
    private LocalDate agreementDateFrom;
    private LocalDate agreementDateTo;
    private LocalDate availabilityDateFrom;
    private LocalDate availabilityDateTo;
    private List<String> typeIds;
    private List<String> statusIds;
    private LocalDate closeDtFrom;
    private LocalDate closeDtTo;
    private String borrowerSearch;
    private String warrantySearch;
    private String sumValue;
    private String sumCurrency;
    private Integer page = 0;
    private Integer size = 20;
    private String sortField = "agreementDate";
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
