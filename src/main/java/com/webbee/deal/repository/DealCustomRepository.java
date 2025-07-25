package com.webbee.deal.repository;

import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.entity.Deal;
import org.springframework.data.domain.Page;

/**
 * Кастомный репозиторий для расширенного поиска сделок.
 * @author Evseeva Tsvetolina
 */
public interface DealCustomRepository {

    /**
     * Осуществляет поиск сделок на основе заданных фильтров.
     */
    Page<Deal> searchDeals(DealSearchRequest filter);

}
