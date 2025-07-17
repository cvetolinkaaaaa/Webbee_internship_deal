package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO, представляющий валюту.
 * Используется для передачи информации о валюте в системе.
 */
@NoArgsConstructor
@AllArgsConstructor
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

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
