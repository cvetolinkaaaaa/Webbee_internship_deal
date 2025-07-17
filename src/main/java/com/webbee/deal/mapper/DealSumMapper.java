package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealSumDto;
import com.webbee.deal.entity.DealSum;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью DealSum и DTO DealSumDto.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface DealSumMapper {

    /**
     * Преобразует сущность DealSum в DTO DealSumDto.
     */
    DealSumDto toDto(DealSum entity);

    /**
     * Преобразует DTO DealSumDto в DealSum.
     */
    DealSum toEntity(DealSumDto dto);

}
