package com.webbee.deal.service;

import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.repository.DealContractorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class DealContractorService {

    private final DealContractorRepository dealContractorRepository;

    public DealContractorService(DealContractorRepository dealContractorRepository) {
        this.dealContractorRepository = dealContractorRepository;
    }

    @Transactional
    public DealContractor save(DealContractor contractor) {
        return dealContractorRepository.save(contractor);
    }

    @Transactional(readOnly = true)
    public List<DealContractor> getByDealId(UUID dealId) {
        return dealContractorRepository.findByDealId(dealId);
    }

    @Transactional
    public void logicalDelete(UUID id) {
        DealContractor contractor = dealContractorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("DealContractor not found"));
        contractor.setIsActive(false);
        dealContractorRepository.save(contractor);
    }
}
