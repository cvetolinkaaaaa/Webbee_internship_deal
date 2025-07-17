package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, отражающая связь между контрагентом и его ролью.
 */
@Entity
@Table(name = "contractor_to_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContractorToRole {

    /**
     * Составной ключ для связи контрагента и роли.
     */
    @EmbeddedId
    private ContractorToRoleId id;
    /**
     * Контрагент, участвующий в сделке.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contractorId")
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private DealContractor dealContractor;
    /**
     * Роль, присвоенная контрагенту.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private ContractorRole role;
    /**
     * Признак активности (true — связь активна, false — неактивна).
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
