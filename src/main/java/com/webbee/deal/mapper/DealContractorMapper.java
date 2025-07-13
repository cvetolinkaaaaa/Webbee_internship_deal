package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.entity.DealContractor;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ContractorToRoleMapper.class})
public interface DealContractorMapper {
    DealContractorDto toDto(DealContractor entity);
    DealContractor toEntity(DealContractorDto dto);

    List<DealContractorDto> toDtoList(List<DealContractor> entityList);
    List<DealContractor> toEntityList(List<DealContractorDto> dtoList);
}
