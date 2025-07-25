package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO, кратко описывающий тип сделки.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
