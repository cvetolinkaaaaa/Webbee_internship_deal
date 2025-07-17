package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Краткое DTO суммы сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
