package com.webbee.deal.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, описывающая сумму сделки.
 */
@Entity
@Table(name = "deal_sum")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealSum {

    /**
     * Уникальный идентификатор суммы сделки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Сделка, к которой относится данная сумма.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;
    /**
     * Значение суммы сделки.
     */
    @Column(name = "sum", nullable = false, precision = 100, scale = 2)
    private BigDecimal value;
    /**
     * Валюта суммы сделки.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
    /**
     * Признак основной суммы для сделки.
     */
    @Column(name = "is_main", nullable = false)
    private Boolean isMain = false;
    /**
     * Признак активности записи.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
