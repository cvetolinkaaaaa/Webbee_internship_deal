package com.webbee.deal.repository;

import com.webbee.deal.entity.ContractorRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с ролями контрагентов.
 */
public interface ContractorRoleRepository extends JpaRepository<ContractorRole, String> {

}
