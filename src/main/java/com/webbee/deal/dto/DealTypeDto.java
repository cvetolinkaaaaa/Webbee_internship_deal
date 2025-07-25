package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO, описывающий тип сделки.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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

}
