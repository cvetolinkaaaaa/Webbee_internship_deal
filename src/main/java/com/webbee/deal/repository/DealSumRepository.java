package com.webbee.deal.repository;

import com.webbee.deal.entity.DealSum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealSumRepository extends JpaRepository<DealSum, Long> {
    List<DealSum> findByDealId(java.util.UUID dealId);
}
