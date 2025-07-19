package com.webbee.deal.repository;

import com.webbee.deal.entity.DealStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью DealStatus.
 */
public interface DealStatusRepository extends JpaRepository<DealStatus, String> {

}
