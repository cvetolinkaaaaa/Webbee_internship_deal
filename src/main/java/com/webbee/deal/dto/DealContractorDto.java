package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webbee.deal.entity.Deal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для связи между сделкой и контрагентом.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealContractorDto {

    /**
     * Уникальный Id связи сделки и контрагента.
     */
    @JsonProperty("id")
    @Schema(description = "Уникальный Id", example = "f647b0d7-5a1e-4b39-abc8-8777f418e225")
    private UUID id;
    /**
     * Сделка, к которой относится контрагент.
     */
    @JsonProperty("deal")
    @Schema(description = "Объект сделки, к которой относится контрагент")
    private Deal deal;
    /**
     * Id контрагента.
     */
    @JsonProperty("contractorId")
    @Schema(description = "Id контрагента", example = "12345678-aaaa-bbbb-cccc-1234567890ab")
    private String contractorId;
    /**
     * Имя контрагента.
     */
    @Schema(description = "Имя контрагента", example = "ООО Пример")
    @JsonProperty("name")
    private String name;
    /**
     * ИНН контрагента.
     */
    @JsonProperty("inn")
    @Schema(description = "ИНН контрагента", example = "7701234567")
    private String inn;
    /**
     * Признак основного контрагента.
     */
    @Schema(description = "Признак основного контрагента", example = "true")
    @JsonProperty("main")
    private Boolean isMain;
    /**
     * Дата создания записи.
     */
    @Schema(description = "Дата создания", example = "2024-07-18T12:00:00")
    @JsonProperty("createDate")
    private LocalDateTime createDate;
    /**
     * Дата последнего изменения записи.
     */
    @JsonProperty("modifyDate")
    @Schema(description = "Дата изменения", example = "2024-07-18T14:30:00")
    private LocalDateTime modifyDate;
    /**
     * ID пользователя, создавшего запись.
     */
    @JsonProperty("createUserId")
    @Schema(description = "ID пользователя, создавшего запись", example = "user1")
    private String createUserId;
    /**
     * ID пользователя, изменившего запись.
     */
    @JsonProperty("modifyUserId")
    @Schema(description = "ID пользователя, изменившего запись", example = "user2")
    private String modifyUserId;
    /**
     * Признак активности связи.
     */
    @JsonProperty("isActive")
    @Schema(description = "Признак активности", example = "true")
    private Boolean isActive;

}
