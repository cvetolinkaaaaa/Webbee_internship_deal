package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webbee.deal.entity.Deal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO для связи между сделкой и контрагентом.
 */
@NoArgsConstructor
@AllArgsConstructor
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
        this.isMain = isMain;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

}
