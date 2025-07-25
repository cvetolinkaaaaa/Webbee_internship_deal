package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.entity.ContractorToRoleId;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью ContractorToRoleId
 * и DTO ContractorToRoleIdDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(componentModel = "spring")
public interface ContractorToRoleIdMapper {

    /**
     * Преобразует сущность ContractorToRoleId в DTO ContractorToRoleIdDto.
     */
    ContractorToRoleIdDto toDto(ContractorToRoleId entity);

    /**
     * Преобразует DTO ContractorToRoleIdDto в сущность ContractorToRoleId.
     */
    ContractorToRoleId toEntity(ContractorToRoleIdDto dto);

}
