package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для представления связи между контрагентом и его ролью.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
