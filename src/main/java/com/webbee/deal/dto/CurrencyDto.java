package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO, представляющий валюту.
 * Используется для передачи информации о валюте в системе.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyDto {

    /**
     * Идентификатор валюты.
     */
    @JsonProperty("id")
    @Schema(
            description = "Идентификатор валюты",
            example = "RUB"
    )
    private String id;
    /**
     * Наименование валюты.
     */
    @JsonProperty("name")
    @Schema(
            description = "Наименование валюты",
            example = "Российский рубль"
    )
    private String name;
    /**
     * Признак активности валюты.
     */
    @JsonProperty("isActive")
    @Schema(
            description = "Признак активности валюты",
            example = "true"
    )
    private Boolean isActive;

}
