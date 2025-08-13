package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.entity.DealContractor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Маппер для преобразования между сущностью DealContractor
 * и DTO DealContractorDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(componentModel = "spring", uses = {ContractorToRoleMapper.class})
public interface DealContractorMapper {

    /**
     * Преобразует сущность DealContractor в DTO DealContractorDto.
     */
    DealContractorDto toDto(DealContractor entity);

    /**
     * Преобразует DTO DealContractorDto в сущность DealContractor.
     */
    DealContractor toEntity(DealContractorDto dto);

    /**
     * Обновляет существующую сущность DealContractor на основе данных из DealContractorDto.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "deal", ignore = true)
    void updateEntityFromDto(DealContractorDto dto, @MappingTarget DealContractor entity);

}
