package com.webbee.deal.repository;

import com.webbee.deal.entity.DealContractor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface DealContractorRepository extends JpaRepository<DealContractor, UUID> {
    List<DealContractor> findByDealId(UUID dealId);
}
