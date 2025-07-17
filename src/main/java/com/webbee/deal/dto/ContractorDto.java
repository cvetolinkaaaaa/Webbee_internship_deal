package com.webbee.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO для передачи данных о контрагенте через REST API.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractorDto {

    /** Уникальный Id контрагента. */
    @Schema(description = "Уникальный Id", example = "CNT12345")
    private String id;
    /** Id родительского контрагента. */
    @Schema(description = "Id родительского контрагента", example = "PARENT_ID")
    private String parentId;
    /** Краткое наименование. */
    @Schema(description = "Краткое наименование", example = "ООО Пример")
    private String name;
    /** Полное наименование. */
    @Schema(description = "Полное наименование", example = "Общество с ограниченной ответственностью Пример")
    private String nameFull;
    /** ИНН. */
    @Schema(description = "ИНН", example = "7701234567")
    private String inn;
    /** ОГРН. */
    @Schema(description = "ОГРН", example = "1027700132195")
    private String ogrn;
    /** Id страны. */
    @Schema(description = "Код страны", example = "RUS")
    private String country;
    /** Id индустрии. */
    @Schema(description = "ID индустрии", example = "1")
    private Integer industry;
    /** Id организации. */
    @Schema(description = "ID организации", example = "2")
    private Integer orgForm;
    /** Признак активности (true — активен). */
    @Schema(description = "Признак активности", example = "true")
    private Boolean isActive;
    /** Дата создания записи. */
    @Schema(description = "Дата создания записи", example = "2025-07-08 20:14:49.525074")
    private LocalDateTime createTime;
    /** Дата последнего изменения записи. */
    @Schema(description = "Дата последнего изменения записи", example = "2025-07-08 20:14:49.525074")
    private LocalDateTime modifyTime;
    /** Id пользователя, создавшего запись. */
    @Schema(description = "Id пользователя, создавшего запись", example = "USER_ID1")
    private String createUserName;
    /** ID пользователя, последнего изменившего запись. */
    @Schema(description = "ID пользователя, последнего изменившего запись", example = "USER_ID2")
    private String modifyUserName;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public Integer getOrgForm() {
        return orgForm;
    }

    public void setOrgForm(Integer orgForm) {
        this.orgForm = orgForm;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

}
