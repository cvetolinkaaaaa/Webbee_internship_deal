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
 * Сущность, представляющая тип сделки.
 */
@Entity
@Table(name = "deal_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealType {

    /**
     * Уникальный идентификатор типа сделки.
     */
    @Id
    @Column(length = 30)
    private String id;
    /**
     * Наименование типа сделки.
     */
    @Column(nullable = false)
    private String name;
    /**
     * Признак активности типа сделки.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
