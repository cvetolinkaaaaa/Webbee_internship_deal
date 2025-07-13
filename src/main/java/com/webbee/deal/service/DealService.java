package com.webbee.deal.service;

import com.webbee.deal.dto.DealDto;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.mapper.DealMapper;
import com.webbee.deal.repository.DealRepository;
import com.webbee.deal.repository.DealStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final DealStatusRepository dealStatusRepository;

    public DealService(DealRepository dealRepository, DealMapper dealMapper, DealStatusRepository dealStatusRepository) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.dealStatusRepository = dealStatusRepository;
    }

    @Transactional
    public DealDto saveDeal(DealDto dto) {

        Deal deal = dealMapper.toEntity(dto);
        if (deal.getId() == null) {
            deal.setCreateDate(LocalDateTime.now());
            DealStatus draftStatus = dealStatusRepository.findById("DRAFT")
                    .orElseThrow(() -> {
                        return new IllegalStateException("DRAFT status not found");
                    });
            deal.setCreateDate(LocalDateTime.now());
            deal.setModifyDate(LocalDateTime.now());
            deal.setStatus(draftStatus);
            deal.setIsActive(true);
        }
        Deal saved = dealRepository.save(deal);
        return dealMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Optional<DealDto> getDeal(UUID id) {
        return dealRepository.findById(id)
                .map(dealMapper::toDto);
    }

    @Transactional
    public DealDto changeDealStatus(UUID dealId, String statusId) {
        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new EntityNotFoundException("Deal not found: " + dealId));

        DealStatus status = dealStatusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("DealStatus not found: " + statusId));

        if (!status.getIsActive()) {
            throw new IllegalStateException("DealStatus is not active: " + statusId);
        }

        deal.setStatus(status);
        deal.setModifyDate(LocalDateTime.now());

        Deal saved = dealRepository.save(deal);
        return dealMapper.toDto(saved);
    }

}
