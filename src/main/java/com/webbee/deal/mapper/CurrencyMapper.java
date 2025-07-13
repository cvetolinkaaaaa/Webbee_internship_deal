package com.webbee.deal.mapper;

import com.webbee.deal.dto.CurrencyDto;
import com.webbee.deal.entity.Currency;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDto toDto(Currency entity);
    Currency toEntity(CurrencyDto dto);
}
