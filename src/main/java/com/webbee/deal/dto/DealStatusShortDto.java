package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Краткая информация о статусе сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DealStatusShortDto {

    /**
     * Идентификатор статуса сделки.
     */
    @Schema(description = "Идентификатор статуса сделки")
    private String id;
    /**
     * Наименование статуса сделки.
     */
    @Schema(description = "Наименование статуса сделки")
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
