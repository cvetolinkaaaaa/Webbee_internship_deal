package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Краткая информация о роли контрагента.
 */
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
