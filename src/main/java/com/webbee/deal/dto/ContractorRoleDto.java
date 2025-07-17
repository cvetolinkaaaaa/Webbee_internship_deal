package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO, представляющий роль контрагента.
 * Используется для передачи информации о роли между слоями приложения.
 */
@NoArgsConstructor
@AllArgsConstructor
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

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
