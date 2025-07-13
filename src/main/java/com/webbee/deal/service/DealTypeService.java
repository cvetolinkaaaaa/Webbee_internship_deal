package com.webbee.deal.service;

import com.webbee.deal.entity.DealType;
import com.webbee.deal.repository.DealTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DealTypeService {
    private final DealTypeRepository dealTypeRepository;

    public DealTypeService(DealTypeRepository dealTypeRepository) {
        this.dealTypeRepository = dealTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<DealType> getAllActive() {
        return dealTypeRepository.findAll(); // или findByIsActiveTrue()
    }
}