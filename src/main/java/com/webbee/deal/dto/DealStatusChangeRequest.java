package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DealStatusChangeRequest {

    @JsonProperty("dealId")
    private UUID dealId;
    @JsonProperty("statusId")
    private String statusId;

}