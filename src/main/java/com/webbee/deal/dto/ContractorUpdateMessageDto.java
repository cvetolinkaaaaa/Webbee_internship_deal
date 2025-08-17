package com.webbee.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для сообщений об обновлении контрагента.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorUpdateMessageDto {
    
    /**
     * Идентификатор контрагента.
     */
    private String contractorId;
    
    /**
     * Наименование контрагента.
     */
    private String name;
    
    /**
     * ИНН контрагента.
     */
    private String inn;
}