package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * Сущность "Роль контрагента".
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contractor_role")
public class ContractorRole {

    /**
     * Уникальный идентификатор роли контрагента.
     */
    @Id
    @Column(length = 30)
    private String id;
    /**
     * Наименование роли контрагента.
     */
    @Column(nullable = false)
    private String name;
    /**
     * Категория роли контрагента.
     */
    @Column(length = 30, nullable = false)
    private String category;
    /**
     * Признак активности роли (true — активна, false — неактивна).
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
