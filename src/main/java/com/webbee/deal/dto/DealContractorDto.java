package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public class DealContractorDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("contractorId")
    private String contractorId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("main")
    private Boolean main;
    @JsonProperty("createDate")
    private LocalDateTime createDate;
    @JsonProperty("modifyDate")
    private LocalDateTime modifyDate;
    @JsonProperty("createUserId")
    private String createUserId;
    @JsonProperty("modifyUserId")
    private String modifyUserId;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("roles")
    private List<ContractorToRoleDto> roles;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

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

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public List<ContractorToRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<ContractorToRoleDto> roles) {
        this.roles = roles;
    }
}
