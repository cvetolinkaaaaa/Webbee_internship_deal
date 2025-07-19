package com.webbee.deal.dto;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Краткое описание контрагента сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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

}
