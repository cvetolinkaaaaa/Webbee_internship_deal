package com.webbee.deal.repository;

import com.webbee.deal.entity.DealSum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью DealSum.
 * @author Evseeva Tsvetolina
 */
public interface DealSumRepository extends JpaRepository<DealSum, Long> {

    /**
     * Находит основную сумму для заданной сделки.
     */
    Optional<DealSum> findByDealIdAndIsMainTrue(UUID dealId);

}
