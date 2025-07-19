package com.webbee.deal.repository;

import com.webbee.deal.entity.DealContractor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с контрагентами сделки.
 */
public interface DealContractorRepository extends JpaRepository<DealContractor, UUID> {

    /**
     * Находит всех активных контрагентов сделки по идентификатору сделки.
     */
    List<DealContractor> findByDealIdAndIsActiveTrue(UUID dealId);

}
