package com.webbee.deal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Сущность "Сделка".
 */
@Entity
@Table(name = "deal")
@NoArgsConstructor
@AllArgsConstructor
public class Deal {

    /**
     * Уникальный идентификатор сделки.
     */
    @Id
    @GeneratedValue
    private UUID id;
    /**
     * Описание сделки.
     */
    @Column
    private String description;
    /**
     * Номер договора по сделке.
     */
    @Column(name = "agreement_number")
    private String agreementNumber;
    /**
     * Дата договора.
     */
    @Column(name = "agreement_date")
    private LocalDate agreementDate;
    /**
     * Дата начала действия договора.
     */
    @Column(name = "agreement_start_dt")
    private LocalDateTime agreementStartDt;
    /**
     * Дата доступности сделки.
     */
    @Column(name = "availability_date")
    private LocalDate availabilityDate;
    /**
     * Тип сделки.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private DealType type;
    /**
     * Статус сделки.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private DealStatus status;
    /**
     * Дата закрытия сделки.
     */
    @Column(name = "close_dt")
    private LocalDateTime closeDt;
    /**
     * Дата создания записи о сделке.
     */
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();
    /**
     * Дата последнего изменения записи о сделке.
     */
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;
    /**
     * Идентификатор пользователя, создавшего запись.
     */
    @Column(name = "create_user_id")
    private String createUserId;
    /**
     * Идентификатор пользователя, изменившего запись.
     */
    @Column(name = "modify_user_id")
    private String modifyUserId;
    /**
     * Признак активности сделки.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

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

    public DealStatus getStatus() {
        return status;
    }

    public void setStatus(DealStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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

    public LocalDateTime getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(LocalDateTime closeDt) {
        this.closeDt = closeDt;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

}
