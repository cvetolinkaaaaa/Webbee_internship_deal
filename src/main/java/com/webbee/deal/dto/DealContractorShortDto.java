package com.webbee.deal.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class DealContractorShortDto {
    private UUID id;
    private String contractorId;
    private String name;
    private Boolean main;
    private List<ContractorRoleShortDto> roles;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public List<ContractorRoleShortDto> getRoles() {
        return roles;
    }

    public void setRoles(List<ContractorRoleShortDto> roles) {
        this.roles = roles;
    }
}