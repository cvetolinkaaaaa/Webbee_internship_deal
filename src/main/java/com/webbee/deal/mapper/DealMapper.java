package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.dto.DealDto;
import com.webbee.deal.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Маппер для преобразования между сущностью Deal, DTO DealDto и DealDetailsDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(
    componentModel = "spring",
    uses = {
        DealTypeMapper.class,
        DealStatusMapper.class,
        DealSumMapper.class,
        DealContractorMapper.class
    }
)
public interface DealMapper {

    /**
     * Преобразует сущность Deal в DTO DealDto.
     */
    DealDto toDto(Deal entity);

    /**
     * Преобразует DTO DealDto в Deal.
     */
    Deal toEntity(DealDto dto);

    /**
     * Обновляет существующую сущность Deal на основе данных из DealDto.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createUserId", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDto(DealDto dto, @MappingTarget Deal entity);

    /**
     * Преобразует сущность Deal в DTO с деталями сделки DealDetailsDto.
     */
    DealDetailsDto toDetailsDto(Deal deal);

}
