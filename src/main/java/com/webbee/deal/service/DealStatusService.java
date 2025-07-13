package com.webbee.deal.service;

import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.repository.DealStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DealStatusService {
    private final DealStatusRepository dealStatusRepository;

    public DealStatusService(DealStatusRepository dealStatusRepository) {
        this.dealStatusRepository = dealStatusRepository;
    }

    @Transactional(readOnly = true)
    public List<DealStatus> getAllActive() {
        return dealStatusRepository.findAll(); // или findByIsActiveTrue()
    }
}
