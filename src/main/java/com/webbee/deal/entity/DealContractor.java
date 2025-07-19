package com.webbee.deal.entity;

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
 * Сущность, представляющая связь сделки с контрагентом.
 */
@Entity
@Table(name = "deal_contractor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealContractor {

    /**
     * Уникальный идентификатор записи.
     */
    @Id
    @GeneratedValue
    private UUID id;
    /**
     * Сделка, к которой относится контрагент.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;
    /**
     * Идентификатор контрагента.
     */
    @Column(name = "contractor_id", length = 12, nullable = false)
    private String contractorId;
    /**
     * Наименование контрагента.
     */
    @Column(nullable = false)
    private String name;
    /**
     * ИНН контрагента.
     */
    @Column
    private String inn;
    /**
     * Признак "основной" (true — если это основной контрагент по сделке).
     */
    @Column(name = "main", nullable = false)
    private Boolean isMain = false;
    /**
     * Дата создания записи.
     */
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();
    /**
     * Дата изменения записи.
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
     * Статус активности записи (true — запись активна).
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
