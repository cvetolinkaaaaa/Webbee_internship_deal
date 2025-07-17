package com.webbee.deal.dto;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Краткое описание контрагента сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DealContractorShortDto {

    /**
     * Уникальный Id связи сделки и контрагента.
     */
    @Schema(description = "Уникальный Id", example = "c60c4b5a-6c4a-4b88-b6fa-ff54f398a5ad")
    private UUID id;
    /**
     * Id контрагента.
     */
    @Schema(description = "Id контрагента", example = "abcdef12-3456-7890-abcd-ef1234567890")
    private String contractorId;
    /**
     * Имя контрагента.
     */
    @Schema(description = "Имя контрагента", example = "ООО Пример")
    private String name;
    /**
     * Признак основного контрагента в сделке.
     */
    @Schema(description = "Признак основного контрагента", example = "true")
    private Boolean main;
    /**
     * Список ролей контрагента в сделке (краткое описание ролей).
     */
    @Schema(description = "Список ролей контрагента в сделке")
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
