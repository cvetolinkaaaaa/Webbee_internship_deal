package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO, кратко описывающий тип сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DealTypeShortDto {

    /**
     * Уникальный идентификатор типа сделки.
     */
    @Schema(description = "Уникальный идентификатор типа сделки")
    private String id;
    /**
     * Название типа сделки.
     */
    @Schema(description = "Название типа сделки")
    private String name;

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
