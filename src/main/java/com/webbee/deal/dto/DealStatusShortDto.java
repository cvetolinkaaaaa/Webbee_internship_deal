package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Краткая информация о статусе сделки.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
