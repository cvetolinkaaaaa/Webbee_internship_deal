package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для представления статуса сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
