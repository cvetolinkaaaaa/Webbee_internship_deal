package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Запрос на смену статуса сделки.
 * @author Evseeva Tsvetolina
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealStatusChangeRequest {

    /**
     * Идентификатор сделки, для которой требуется сменить статус.
     */
    @Schema(description = "ID сделки", example = "e8ff7e4e-3a21-4c6e-b5cd-2a03fd8e5bb7")
    @JsonProperty("dealId")
    private UUID dealId;
    /**
     * Идентификатор нового статуса сделки.
     */
    @Schema(description = "ID нового статуса сделки")
    @JsonProperty("statusId")
    private String statusId;

}
