package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class ContractorToRoleIdDto {
    @JsonProperty("contractorId")
    private UUID contractorId;
    @JsonProperty("roleId")
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
