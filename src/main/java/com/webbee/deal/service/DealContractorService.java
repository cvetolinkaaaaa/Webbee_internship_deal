package com.webbee.deal.service;

import com.webbee.deal.client.ContractorClient;
import com.webbee.deal.dto.ContractorDto;
import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.mapper.DealContractorMapper;
import com.webbee.deal.repository.DealContractorRepository;
import com.webbee.deal.repository.DealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class DealContractorService {

    private final DealContractorRepository dealContractorRepository;
    private final DealRepository dealRepository;
    private final ContractorClient contractorClient;
    private final DealContractorMapper dealContractorMapper;

    public DealContractorService(DealContractorRepository dealContractorRepository, DealRepository dealRepository, ContractorClient contractorClient,DealContractorMapper dealContractorMapper) {
        this.dealContractorRepository = dealContractorRepository;
        this.dealRepository = dealRepository;
        this.contractorClient = contractorClient;
        this.dealContractorMapper = dealContractorMapper;
    }

    @Transactional
    public void saveDealContractor(DealContractorDto dealContractorDto) {
        ContractorDto contractorDto = contractorClient.getContractor(dealContractorDto.getContractorId());
        DealContractor entity;
        if (Objects.isNull(contractorDto) || !contractorDto.getName().equals(dealContractorDto.getName())) {
            throw new IllegalArgumentException("Contractor with id " + dealContractorDto.getContractorId() + " does not exist in contractor service");
        }

        if (dealRepository.findById(dealContractorDto.getDeal().getId()).isEmpty()) {
            throw new IllegalArgumentException("Deal with id " + dealContractorDto.getDeal() + " does not exist");
        }
        if (dealContractorDto.getId() != null) {
            // UPDATE
            entity = dealContractorRepository.findById(dealContractorDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("DealContractor not found: " + dealContractorDto.getId()));
            // обновить только разрешённые поля
            dealContractorMapper.updateEntityFromDto(dealContractorDto, entity);
            entity.setModifyDate(LocalDateTime.now());
        } else {
            // CREATE
            entity = dealContractorMapper.toEntity(dealContractorDto);
            entity.setCreateDate(LocalDateTime.now());
            entity.setModifyDate(LocalDateTime.now());
            entity.setIsActive(true);
        }

        DealContractor saved = dealContractorRepository.save(entity);
    }
    @Transactional
    public void deleteDealContractor(UUID dealContractorId) {
        DealContractor dealContractor = dealContractorRepository.findById(dealContractorId)
                .orElseThrow(() -> new EntityNotFoundException("DealContractor not found: " + dealContractorId));

        if (!Boolean.FALSE.equals(dealContractor.getIsActive())) {
            dealContractor.setIsActive(false);
            dealContractor.setModifyDate(LocalDateTime.now());
            dealContractorRepository.save(dealContractor);
        }
    }

}
