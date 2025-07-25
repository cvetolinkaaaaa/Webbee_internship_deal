package com.webbee.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Составной идентификатор для связи "Контрагент — Роль".
 * @author Evseeva Tsvetolina
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
