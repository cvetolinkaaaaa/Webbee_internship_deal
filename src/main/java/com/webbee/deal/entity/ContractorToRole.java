package com.webbee.deal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contractor_to_role")
@NoArgsConstructor
@AllArgsConstructor
public class ContractorToRole {

    @EmbeddedId
    private ContractorToRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contractorId")
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private DealContractor dealContractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private ContractorRole role;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public ContractorToRoleId getId() {
        return id;
    }

    public void setId(ContractorToRoleId id) {
        this.id = id;
    }

    public DealContractor getDealContractor() {
        return dealContractor;
    }

    public void setDealContractor(DealContractor dealContractor) {
        this.dealContractor = dealContractor;
    }

    public ContractorRole getRole() {
        return role;
    }

    public void setRole(ContractorRole role) {
        this.role = role;
    }
}
