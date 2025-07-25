package com.webbee.deal.service;

import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.dto.DealDto;
import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.mapper.DealMapper;
import com.webbee.deal.repository.DealRepository;
import com.webbee.deal.repository.DealStatusRepository;
import com.webbee.deal.security.service.AuthorizationService;
import com.webbee.deal.utils.UserIdService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервисный класс для управления сделками.
 * @author Evseeva Tsvetolina
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final DealStatusRepository dealStatusRepository;
    private final AuthorizationService authorizationService;
    private final UserIdService userIdService;

    /**
     * Создает или обновляет сделку.
     */
    @Transactional
    public void saveDeal(DealDto dto) {

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
        dealRepository.save(deal);

    }

    /**
     * Создает или обновляет сделку с учетом прав пользователя.
     */
    public void saveDealWithAuth(DealDto dto) {
        String userId = userIdService.getCurrentUserId();
        if (dto.getId() == null) {
            dto.setCreateUserId(userId);
        }
        dto.setModifyUserId(userId);
        saveDeal(dto);

    }

    /**
     * Изменяет статус сделки по идентификатору сделки и статуса.
     */
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

    /**
     * Выполняет поиск сделок по заданному фильтру и возвращает постраничный результат.
     */
    public Page<DealDetailsDto> searchDeals(DealSearchRequest filter) {

        Page<Deal> deals = dealRepository.searchDeals(filter);
        return deals.map(dealMapper::toDetailsDto);

    }

    /**
     * Выполняет поиск сделок с учетом прав доступа пользователя.
     */
    public Page<DealDetailsDto> searchDealsWithAuth(DealSearchRequest filter) {

        DealSearchRequest filteredRequest = authorizationService.applyDealAccessFilter(filter);
        return searchDeals(filteredRequest);

    }

}
