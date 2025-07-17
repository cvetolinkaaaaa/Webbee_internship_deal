package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Сущность, описывающая статус сделки.
 */
@Entity
@Table(name = "deal_status")
@NoArgsConstructor
@AllArgsConstructor
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
