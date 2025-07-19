package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, описывающая статус сделки.
 */
@Entity
@Table(name = "deal_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealStatus {

    /**
     * Уникальный идентификатор статуса.
     */
    @Id
    @Column(length = 30)
    private String id;
    /**
     * Наименование статуса.
     */
    @Column(nullable = false)
    private String name;
    /**
     * Статус активности (true — статус активен и доступен для использования).
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
