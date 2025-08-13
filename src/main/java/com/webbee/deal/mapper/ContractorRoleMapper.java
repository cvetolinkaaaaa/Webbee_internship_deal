package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorRoleDto;
import com.webbee.deal.entity.ContractorRole;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью ContractorRole
 * и DTO ContractorRoleDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(componentModel = "spring")
public interface ContractorRoleMapper {

    /**
     * Преобразует сущность ContractorRole в DTO ContractorRoleDto.
     */
    ContractorRoleDto toDto(ContractorRole entity);

    /**
     * Преобразует DTO ContractorRoleDto в  сущность ContractorRole.
     */
    ContractorRole toEntity(ContractorRoleDto dto);

}
