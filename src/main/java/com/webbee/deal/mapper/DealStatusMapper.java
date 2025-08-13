package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealStatusDto;
import com.webbee.deal.entity.DealStatus;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью DealStatus и DTO DealStatusDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(componentModel = "spring")
public interface DealStatusMapper {

    /**
     * Преобразует сущность DealStatus в DTO DealStatusDto.
     */
    DealStatusDto toDto(DealStatus entity);

    /**
     * Преобразует DTO DealStatusDto в DealStatus.
     */
    DealStatus toEntity(DealStatusDto dto);

}
