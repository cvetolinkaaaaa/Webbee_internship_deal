package com.webbee.deal.mapper;

import com.webbee.deal.dto.CurrencyDto;
import com.webbee.deal.entity.Currency;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Currency
 * и DTO CurrencyDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    /**
     * Преобразует сущность Currency в DTO CurrencyDto.
     */
    CurrencyDto toDto(Currency entity);

    /**
     * Преобразует DTO CurrencyDto в сущность Currency.
     */
    Currency toEntity(CurrencyDto dto);

}
