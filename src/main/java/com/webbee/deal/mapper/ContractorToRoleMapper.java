package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.entity.ContractorToRole;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {
        ContractorRoleMapper.class,
        ContractorToRoleIdMapper.class
})
public interface ContractorToRoleMapper {
    ContractorToRoleDto toDto(ContractorToRole entity);
    ContractorToRole toEntity(ContractorToRoleDto dto);

    List<ContractorToRoleDto> toDtoList(List<ContractorToRole> entityList);
    List<ContractorToRole> toEntityList(List<ContractorToRoleDto> dtoList);
}
