package com.webbee.deal.repository;

import com.webbee.deal.entity.DealStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealStatusRepository extends JpaRepository<DealStatus, String> {
}
