package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO, описывающий тип сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DealTypeDto {

    /**
     * Уникальный идентификатор типа сделки.
     */
    @JsonProperty("id")
    @Schema(description = "Уникальный идентификатор типа сделки")
    private String id;
    /**
     * Название типа сделки.
     */
    @JsonProperty("name")
    @Schema(description = "Название типа сделки")
    private String name;
    /**
     * Флаг, указывающий, активен ли тип сделки.
     */
    @JsonProperty("isActive")
    @Schema(description = "Признак активности типа сделки", example = "true")
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

}
