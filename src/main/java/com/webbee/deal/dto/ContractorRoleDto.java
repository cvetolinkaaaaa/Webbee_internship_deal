package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO, представляющий роль контрагента.
 * Используется для передачи информации о роли между слоями приложения.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContractorRoleDto {

    /**
     * Уникальный идентификатор роли.
     */
    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор роли", example = "BORROWER")
    private String id;
    /**
     * Наименование роли.
     */
    @JsonProperty("name")
    @Schema(description = "Наименование роли", example = "Заемщик")
    private String name;
    /**
     * Категория роли.
     */
    @Schema(description = "Категория роли", example = "BORROWER")
    @JsonProperty("category")
    private String category;
    /**
     * Флаг активности роли.
     */
    @Schema(description = "Признак, активна ли роль", example = "true")
    @JsonProperty("isActive")
    private Boolean isActive;

}
