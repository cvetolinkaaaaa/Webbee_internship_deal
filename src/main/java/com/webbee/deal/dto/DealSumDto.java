package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO для представления суммы сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean main) {
        isMain = main;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
        this.currency = currency;
    }

}
