
package com.webbee.deal.service;

import com.webbee.audit_lib.starter.core.AuditLog;
import com.webbee.deal.dto.ContractorDto;
import com.webbee.deal.dto.ContractorUpdateMessageDto;
import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.repository.ContractorToRoleRepository;
import com.webbee.deal.repository.DealContractorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для обновления данных контрагентов на основе входящих событий.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContractorUpdateService {

    private final DealContractorRepository dealContractorRepository;
    private final ContractorToRoleRepository contractorToRoleRepository;

    /**
     * Создает новый контрагент на основе полученных данных.
     */
    @AuditLog(logLevel = AuditLog.LogLevel.INFO)
    @Transactional
    public void createContractor(ContractorDto contractorData) {
        log.info("Создание нового контрагента: id={}, name={}",
                 contractorData.getId(), contractorData.getName());
        log.info("Получено уведомление о создании контрагента {} ({}). " +
                "Записи в deal_contractor будут созданы при привязке к сделкам.",
                 contractorData.getId(), contractorData.getName());
    }

    /**
     * Обновляет существующий контрагент.
     */
    @AuditLog(logLevel = AuditLog.LogLevel.INFO)
    @Transactional
    public void updateContractor(ContractorDto contractorData) {
        log.info("Обновление контрагента: id={}, name={}, inn={}, isActive={}",
                contractorData.getId(), contractorData.getName(),
                 contractorData.getInn(), contractorData.getIsActive());
        List<DealContractor> dealContractors = dealContractorRepository
                .findByContractorIdAndIsActiveTrue(contractorData.getId());
        if (dealContractors.isEmpty()) {
            log.info("Контрагент с ID {} не найден в активных сделках. " +
                     "Возможно, он еще не привязан ни к одной сделке.", contractorData.getId());
            return;
        }
        log.info("Найдено {} записей deal_contractor для обновления", dealContractors.size());
        for (DealContractor dealContractor : dealContractors) {
            updateDealContractorFromDto(dealContractor, contractorData);
        }
        dealContractorRepository.saveAll(dealContractors);
        if (Boolean.FALSE.equals(contractorData.getIsActive())) {
            deactivateContractorRoles(dealContractors);
        }
        log.info("Завершено обновление контрагента: {}. Обновлено записей: {}",
                contractorData.getId(), dealContractors.size());
    }

    /**
     * Обновляет информацию о контрагенте во всех связанных сделках.
     * Метод для обратной совместимости со старым форматом сообщений.
     */
    @AuditLog(logLevel = AuditLog.LogLevel.INFO)
    @Transactional
    public void updateContractorInfo(ContractorUpdateMessageDto contractorUpdate) {
        log.info("Обновление информации о контрагенте (старый формат): {}", contractorUpdate.getContractorId());
        List<DealContractor> contractorsToUpdate = dealContractorRepository
                .findByContractorIdAndIsActiveTrue(contractorUpdate.getContractorId());
        if (contractorsToUpdate.isEmpty()) {
            log.info("Контрагент с ID {} не найден в активных сделках", contractorUpdate.getContractorId());
            return;
        }

        for (DealContractor dealContractor : contractorsToUpdate) {
            boolean wasUpdated = false;

            if (contractorUpdate.getName() != null && !contractorUpdate.getName().equals(dealContractor.getName())) {
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

    /**
     * Обновляет поля DealContractor из ContractorDto.
     */
    private void updateDealContractorFromDto(DealContractor dealContractor, ContractorDto contractorData) {
        boolean wasUpdated = false;
        if (contractorData.getName() != null &&
            !contractorData.getName().equals(dealContractor.getName())) {

            log.debug("Обновление name: '{}' -> '{}' для контрагента {} в сделке {}",
                    dealContractor.getName(), contractorData.getName(),
                    contractorData.getId(), dealContractor.getDeal().getId());

            dealContractor.setName(contractorData.getName());
            wasUpdated = true;
        }

        if (contractorData.getInn() != null &&
            !contractorData.getInn().equals(dealContractor.getInn())) {

            log.debug("Обновление inn: '{}' -> '{}' для контрагента {} в сделке {}",
                    dealContractor.getInn(), contractorData.getInn(),
                    contractorData.getId(), dealContractor.getDeal().getId());

            dealContractor.setInn(contractorData.getInn());
            wasUpdated = true;
        }

        if (contractorData.getIsActive() != null &&
            !contractorData.getIsActive().equals(dealContractor.getIsActive())) {
            log.debug("Обновление isActive: {} -> {} для контрагента {} в сделке {}",
                    dealContractor.getIsActive(), contractorData.getIsActive(),
                    contractorData.getId(), dealContractor.getDeal().getId());

            dealContractor.setIsActive(contractorData.getIsActive());
            wasUpdated = true;
        }

        if (wasUpdated) {
            dealContractor.setModifyDate(LocalDateTime.now());
        }
    }

    /**
    * Деактивирует все роли контрагентов.
     */
    private void deactivateContractorRoles(List<DealContractor> dealContractors) {
        for (DealContractor dealContractor : dealContractors) {
            List<ContractorToRole> roles = contractorToRoleRepository
                    .findByIdContractorIdAndIsActiveTrue(dealContractor.getId());

            if (!roles.isEmpty()) {
                log.info("Деактивация {} ролей для контрагента в сделке {}",
                         roles.size(), dealContractor.getDeal().getId());
                for (ContractorToRole role : roles) {
                    role.setIsActive(false);
                }

                contractorToRoleRepository.saveAll(roles);
            }
        }
    }

    /**
     * Возвращает все активные записи DealContractor для контрагента.
     */
    public List<DealContractor> findActiveContractorDeals(String contractorId) {
        return dealContractorRepository.findByContractorIdAndIsActiveTrue(contractorId);
    }

    /**
    * Проверяет, существует ли контрагент в активных сделках.
    */
    public boolean contractorExistsInDeals(String contractorId) {
        return !dealContractorRepository.findByContractorIdAndIsActiveTrue(contractorId).isEmpty();
    }

    /**
    * Возвращает количество активных сделок для контрагента.
    */
    public long countActiveContractorDeals(String contractorId) {
        return dealContractorRepository.findByContractorIdAndIsActiveTrue(contractorId).size();
    }

}
