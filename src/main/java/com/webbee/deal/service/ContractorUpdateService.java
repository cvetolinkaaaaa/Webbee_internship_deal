package com.webbee.deal.service;

import com.webbee.audit_lib.starter.core.AuditLog;
import com.webbee.deal.dto.ContractorUpdateMessageDto;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.repository.DealContractorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для обновления информации о контрагентах.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContractorUpdateService {

    private final DealContractorRepository dealContractorRepository;

    /**
     * Обновляет информацию о контрагенте во всех связанных сделках.
     *
     * @param contractorUpdate данные для обновления контрагента
     */
    @AuditLog(logLevel = AuditLog.LogLevel.INFO)
    @Transactional
    public void updateContractorInfo(ContractorUpdateMessageDto contractorUpdate) {
        log.info("Начало обновления информации о контрагенте: {}", contractorUpdate.getContractorId());
        
        List<DealContractor> contractorsToUpdate = dealContractorRepository
                .findByContractorIdAndIsActiveTrue(contractorUpdate.getContractorId());
        
        if (contractorsToUpdate.isEmpty()) {
            log.info("Контрагент с ID {} не найден в активных сделках", contractorUpdate.getContractorId());
            return;
        }
        
        for (DealContractor dealContractor : contractorsToUpdate) {
            boolean wasUpdated = false;
            
            if (contractorUpdate.getName() != null && 
                !contractorUpdate.getName().equals(dealContractor.getName())) {
                dealContractor.setName(contractorUpdate.getName());
                wasUpdated = true;
            }
            
            if (contractorUpdate.getInn() != null && 
                !contractorUpdate.getInn().equals(dealContractor.getInn())) {
                dealContractor.setInn(contractorUpdate.getInn());
                wasUpdated = true;
            }
            
            if (wasUpdated) {
                dealContractor.setModifyDate(LocalDateTime.now());
                log.debug("Обновлен контрагент в сделке {}: name={}, inn={}", 
                         dealContractor.getDeal().getId(), 
                         contractorUpdate.getName(), 
                         contractorUpdate.getInn());
            }
        }
        
        dealContractorRepository.saveAll(contractorsToUpdate);
        log.info("Завершено обновление информации о контрагенте: {}. Обновлено записей: {}", 
                 contractorUpdate.getContractorId(), contractorsToUpdate.size());
    }
}