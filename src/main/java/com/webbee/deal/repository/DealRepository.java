package com.webbee.deal.repository;

import com.webbee.deal.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью Deal.
 * @author Evseeva Tsvetolina
 */
public interface DealRepository extends JpaRepository<Deal, UUID>, DealCustomRepository{

}
