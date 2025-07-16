package com.webbee.deal.service;

import com.webbee.deal.dto.*;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.entity.DealSum;
import com.webbee.deal.mapper.DealMapper;
import com.webbee.deal.repository.DealRepository;
import com.webbee.deal.repository.DealStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Deal deal;
        if (dto.getId() != null) {
            deal = dealRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Deal not found: " + dto.getId()));
            dealMapper.updateEntityFromDto(dto, deal);
            deal.setModifyDate(LocalDateTime.now());
        } else {
            DealStatus draftStatus = dealStatusRepository.findById("DRAFT")
                   .orElseThrow(() -> {
                        return new IllegalStateException("DRAFT status not found");
                  });
            deal = dealMapper.toEntity(dto);
            deal.setCreateDate(LocalDateTime.now());
            deal.setModifyDate(LocalDateTime.now());
            deal.setStatus(draftStatus);
            deal.setIsActive(true);
        }
        Deal saved = dealRepository.save(deal);
        return dealMapper.toDto(saved);
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

    public Page<DealDetailsDto> searchDeals(DealSearchRequest filter) {
        Page<Deal> deals = dealRepository.searchDeals(filter);
        return deals.map(dealMapper::toDetailsDto);
    }

}
