package com.webbee.deal.repository;

import com.webbee.deal.entity.DealSum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DealSumRepository extends JpaRepository<DealSum, Long> {

    Optional<DealSum> findByDealIdAndIsMainTrue(UUID dealId);

}
