package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Краткая информация о роли контрагента.
 * @author Evseeva Tsvetolina
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractorRoleShortDto {

    /**
     * Уникальный идентификатор роли.
     */
    @Schema(description = "Уникальный идентификатор роли", example = "BORROWER")
    private String id;
    /**
     * Наименование роли.
     */
    @Schema(description = "Наименование роли", example = "Заемщик")
    private String name;
    /**
     * Категория роли.
     */
    @Schema(description = "Категория роли", example = "BORROWER")
    private String category;

}
