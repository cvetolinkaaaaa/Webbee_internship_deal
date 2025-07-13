package com.webbee.deal.repository;

import com.webbee.deal.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DealRepository extends JpaRepository<Deal, UUID> {
    // Здесь можно добавить методы для поиска/фильтрации
}
