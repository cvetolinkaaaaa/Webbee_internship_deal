package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Составной идентификатор для связи "Контрагент — Роль".
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContractorToRoleId implements Serializable {

    /**
     * Идентификатор контрагента.
     */
    @Column(name = "contractor_id")
    private UUID contractorId;
    /**
     * Идентификатор роли.
     */
    @Column(name = "role_id")
    private String roleId;

    public UUID getContractorId() {
        return contractorId;
    }

    public void setContractorId(UUID contractorId) {
        this.contractorId = contractorId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
