package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.entity.ContractorToRole;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью ContractorToRole
 * и DTO ContractorToRoleDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(componentModel = "spring", uses = {
        ContractorRoleMapper.class,
        ContractorToRoleIdMapper.class
})
public interface ContractorToRoleMapper {

    /**
     * Преобразует сущность ContractorToRole в DTO ContractorToRole.
     */
    ContractorToRoleDto toDto(ContractorToRole entity);

    /**
     * Преобразует DTO ContractorToRole в сущность ContractorToRole.
     */
    ContractorToRole toEntity(ContractorToRoleDto dto);

}
