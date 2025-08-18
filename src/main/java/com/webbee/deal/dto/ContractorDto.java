package com.webbee.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO для данных контрагента.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    /** Признак активности (true — активен). Поддерживает оба поля из JSON. */
    @JsonProperty("isActive")
    @Schema(description = "Признак активности", example = "true")
    private Boolean isActive;

    /** Альтернативное поле для активности (для совместимости). */
    @JsonProperty("active")
    private Boolean active;

    /** Дата создания записи. */
    @Schema(description = "Дата создания записи", example = "2025-07-08T20:14:49")
    private LocalDateTime createTime;

    /** Дата последнего изменения записи. */
    @Schema(description = "Дата последнего изменения записи", example = "2025-07-08T20:14:49")
    private LocalDateTime modifyTime;

    /** Id пользователя, создавшего запись. */
    @Schema(description = "Id пользователя, создавшего запись", example = "USER_ID1")
    private String createUserId;

    /** ID пользователя, последнего изменившего запись. */
    @Schema(description = "ID пользователя, последнего изменившего запись", example = "USER_ID2")
    private String modifyUserId;

    /**
     * Возвращает статус активности, проверяя оба поля.
     */
    public Boolean getIsActive() {
        if (isActive != null) {
            return isActive;
        }
        return active;
    }

    /**
     * Устанавливает статус активности в оба поля для совместимости.
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
        this.active = isActive;
    }

    public Boolean getActive() {
        return getIsActive();
    }

    public void setActive(Boolean active) {
        setIsActive(active);
    }

}
