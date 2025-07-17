package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO, представляющий составной идентификатор связи между контрагентом и ролью.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractorToRoleIdDto {

    /**
     * Идентификатор контрагента.
     */
    @Schema(
            description = "Идентификатор контрагента",
            example = "d290f1ee-6c54-4b01-90e6-d701748f0851"
    )
    @JsonProperty("contractorId")
    private UUID contractorId;
    /**
     * Идентификатор роли контрагента.
     */
    @JsonProperty("roleId")
    @Schema(
            description = "Идентификатор роли контрагента",
            example = "BORROWER"
    )
    private String roleId;

}
