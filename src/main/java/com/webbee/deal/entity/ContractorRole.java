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
 * Сущность "Роль контрагента".
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
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

}
