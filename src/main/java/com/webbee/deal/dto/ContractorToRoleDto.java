package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO для представления связи между контрагентом и его ролью.
 */
@NoArgsConstructor
@AllArgsConstructor
public class ContractorToRoleDto {

    /**
     * Составной идентификатор связи между контрагентом и ролью.
     */
    @Schema(description = "Составной идентификатор")
    @JsonProperty("id")
    private ContractorToRoleIdDto id;
    /**
     * Роль контрагента.
     */
    @JsonProperty("role")
    @Schema(description = "Роль контрагента")
    private ContractorRoleDto role;
    /**
     * Признак активности связи.
     */
    @JsonProperty("isActive")
    @Schema(description = "Признак активности связи", example = "true")
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
