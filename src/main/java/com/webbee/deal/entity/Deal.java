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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность "Сделка".
 */
@Entity
@Table(name = "deal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
