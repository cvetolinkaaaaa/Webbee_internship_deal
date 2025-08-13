package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Краткое DTO суммы сделки.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealSumShortDto {

    /**
     * Значение суммы сделки.
     */
    @Schema(description = "Значение суммы сделки", example = "1000000.00")
    private String value;
    /**
     * Валюта суммы сделки.
     */
    @Schema(description = "Валюта суммы сделки", example = "RUB")
    private String currency;

}
