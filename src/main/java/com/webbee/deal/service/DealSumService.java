package com.webbee.deal.service;

import com.webbee.deal.entity.DealSum;
import com.webbee.deal.repository.DealSumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class DealSumService {

    private final DealSumRepository dealSumRepository;

    public DealSumService(DealSumRepository dealSumRepository) {
        this.dealSumRepository = dealSumRepository;
    }

    @Transactional(readOnly = true)
    public List<DealSum> getSumsByDealId(UUID dealId) {
        return dealSumRepository.findByDealId(dealId);
    }
}
