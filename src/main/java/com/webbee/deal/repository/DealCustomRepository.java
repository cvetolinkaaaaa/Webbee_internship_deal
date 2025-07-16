package com.webbee.deal.repository;

import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.entity.Deal;
import org.springframework.data.domain.Page;

public interface DealCustomRepository {

    Page<Deal> searchDeals(DealSearchRequest filter);

}
