package com.webbee.deal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "deal")
@NoArgsConstructor
@AllArgsConstructor
public class Deal {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String description;

    @Column(name = "agreement_number")
    private String agreementNumber;

    @Column(name = "agreement_date")
    private LocalDate agreementDate;

    @Column(name = "agreement_start_dt")
    private LocalDateTime agreementStartDt;

    @Column(name = "availability_date")
    private LocalDate availabilityDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private DealType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private DealStatus status;

    @Column(name = "close_dt")
    private LocalDateTime closeDt;

    // === Служебные поля ===
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "create_user_id")
    private String createUserId;

    @Column(name = "modify_user_id")
    private String modifyUserId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

//    @OneToMany(mappedBy = "deal", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<DealSum> sums = new ArrayList<>();
//
//    @OneToMany(mappedBy = "deal", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<DealContractor> contractors = new ArrayList<>();

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public void setStatus(DealStatus status) {
        this.status = status;
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

    public DealType getType() {
        return type;
    }

    public void setType(DealType type) {
        this.type = type;
    }

    public DealStatus getStatus() {
        return status;
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

//    public List<DealSum> getSums() {
//        return sums;
//    }
//
//    public void setSums(List<DealSum> sums) {
//        this.sums = sums;
//    }
//
//    public List<DealContractor> getContractors() {
//        return contractors;
//    }
//
//    public void setContractors(List<DealContractor> contractors) {
//        this.contractors = contractors;
//    }
}
