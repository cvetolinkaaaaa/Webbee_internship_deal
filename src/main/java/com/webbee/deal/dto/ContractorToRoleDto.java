package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
