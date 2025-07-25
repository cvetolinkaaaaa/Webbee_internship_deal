package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для представления суммы сделки.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealSumDto {

    /**
     * Уникальный идентификатор суммы.
     */
    @Schema(description = "Уникальный идентификатор суммы", example = "1")
    @JsonProperty("id")
    private Long id;
    /**
     * Значение суммы сделки.
     */
    @Schema(description = "Значение суммы сделки", example = "1000000.00")
    @JsonProperty("value")
    private BigDecimal value;
    /**
     * Валюта суммы сделки.
     */
    @Schema(description = "Валюта суммы сделки")
    @JsonProperty("currency")
    private CurrencyDto currency;
    /**
     * Признак основной суммы.
     */
    @Schema(description = "Признак основной суммы", example = "true")
    @JsonProperty("isMain")
    private Boolean isMain;
    /**
     * Признак активности записи.
     */
    @Schema(description = "Признак активности записи", example = "true")
    @JsonProperty("isActive")
    private Boolean isActive;

}
