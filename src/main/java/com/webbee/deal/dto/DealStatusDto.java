package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO для представления статуса сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DealStatusDto {

    /**
     * Уникальный идентификатор статуса.
     */
    @Schema(description = "Идентификатор статуса сделки")
    @JsonProperty("id")
    private String id;
    /**
     * Наименование статуса.
     */
    @Schema(description = "Наименование статуса сделки")
    @JsonProperty("name")
    private String name;
    /**
     * Признак активности статуса.
     */
    @Schema(description = "Признак активности статуса")
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

}
