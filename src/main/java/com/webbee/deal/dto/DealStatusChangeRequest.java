package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Запрос на смену статуса сделки.
 */
@NoArgsConstructor
@AllArgsConstructor
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

    public UUID getDealId() {
        return dealId;
    }

    public void setDealId(UUID dealId) {
        this.dealId = dealId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

}
