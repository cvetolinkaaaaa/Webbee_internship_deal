package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class ContractorToRoleDto {
    @JsonProperty("id")
    private ContractorToRoleIdDto id;
    @JsonProperty("role")
    private ContractorRoleDto role;
    @JsonProperty("isActive")
    private Boolean isActive;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public ContractorToRoleIdDto getId() {
        return id;
    }

    public void setId(ContractorToRoleIdDto id) {
        this.id = id;
    }

    public ContractorRoleDto getRole() {
        return role;
    }

    public void setRole(ContractorRoleDto role) {
        this.role = role;
    }
}
