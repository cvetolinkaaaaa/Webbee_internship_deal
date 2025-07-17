package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая тип сделки.
 */
@Entity
@Table(name = "deal_type")
@NoArgsConstructor
@AllArgsConstructor
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
