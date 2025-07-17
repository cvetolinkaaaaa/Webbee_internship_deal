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
 * Сущность "Валюта".
 */
@Entity
@Table(name = "currency")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Currency {

    /**
     * Уникальный идентификатор валюты (три символа, например, "RUB").
     */
    @Id
    @Column(length = 3)
    private String id;
    /**
     * Наименование валюты.
     */
    @Column(nullable = false)
    private String name;
    /**
     * Признак активности валюты.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
