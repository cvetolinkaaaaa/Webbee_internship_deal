package com.webbee.deal.service;

import com.webbee.deal.dto.*;
import com.webbee.deal.entity.*;
import com.webbee.deal.repository.ContractorToRoleRepository;
import com.webbee.deal.repository.DealContractorRepository;
import com.webbee.deal.repository.DealRepository;
import com.webbee.deal.repository.DealSumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DealDetailsService {

    private final DealRepository dealRepository;
    private final DealSumRepository dealSumRepository;
    private final DealContractorRepository dealContractorRepository;
    private final ContractorToRoleRepository contractorToRoleRepository;

    public DealDetailsService(DealRepository dealRepository, DealSumRepository dealSumRepository, DealContractorRepository dealContractorRepository, ContractorToRoleRepository contractorToRoleRepository) {
        this.dealRepository = dealRepository;
        this.dealSumRepository = dealSumRepository;
        this.dealContractorRepository = dealContractorRepository;
        this.contractorToRoleRepository = contractorToRoleRepository;
    }

    public DealDetailsDto getDealDetails(UUID dealId) {
        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new EntityNotFoundException("Deal not found: " + dealId));
        DealSum mainSum = dealSumRepository.findByDealIdAndIsMainTrue(dealId).orElse(null);
        List<DealContractor> contractors = dealContractorRepository.findByDealIdAndIsActiveTrue(dealId);
        DealDetailsDto dto = new DealDetailsDto();
        dto.setId(deal.getId());
        dto.setDescription(deal.getDescription());
        dto.setAgreementNumber(deal.getAgreementNumber());
        dto.setAgreementDate(deal.getAgreementDate());
        dto.setAgreementStartDt(deal.getAgreementStartDt());
        dto.setAvailabilityDate(deal.getAvailabilityDate());
        dto.setCloseDt(deal.getCloseDt());
        if (deal.getType() != null) {
            DealType type = deal.getType();
            dto.setType(new DealTypeShortDto(type.getId(), type.getName()));
        }
        if (deal.getStatus() != null) {
            DealStatus status = deal.getStatus();
            dto.setStatus(new DealStatusShortDto(status.getId(), status.getName()));
        }
        if (mainSum != null) {
            dto.setSum(new DealSumShortDto(
                mainSum.getValue().toString(),
                mainSum.getCurrency() != null ? mainSum.getCurrency().getId() : null
            ));
        }
        List<DealContractorShortDto> contractorDtos = contractors.stream()
            .map(dc -> {
                DealContractorShortDto cDto = new DealContractorShortDto();
                cDto.setId(dc.getId());
                cDto.setContractorId(dc.getContractorId());
                cDto.setName(dc.getName());
                cDto.setMain(dc.getIsMain());
                List<ContractorToRole> roles = contractorToRoleRepository.findByIdContractorIdAndIsActiveTrue(dc.getId());
                List<ContractorRoleShortDto> roleDtos = roles.stream()
                    .map(ctr -> {
                        ContractorRole role = ctr.getRole();
                        ContractorRoleShortDto rDto = new ContractorRoleShortDto();
                        rDto.setId(role.getId());
                        rDto.setName(role.getName());
                        rDto.setCategory(role.getCategory());
                        return rDto;
                    })
                    .toList();
                cDto.setRoles(roleDtos);
                return cDto;
            })
            .toList();
        dto.setContractors(contractorDtos);
        return dto;
    }

}
