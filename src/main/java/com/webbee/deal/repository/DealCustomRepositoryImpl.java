package com.webbee.deal.repository;

import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.entity.Deal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Реализация кастомного репозитория для поиска сделок с фильтрацией по различным параметрам.
 */
@Repository
public class DealCustomRepositoryImpl implements DealCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Осуществляет поиск сделок по различным фильтрам.
     */
    @Override
    public Page<Deal> searchDeals(DealSearchRequest filter) {
        StringBuilder jpql = new StringBuilder("SELECT d FROM Deal d WHERE d.isActive = true");
        StringBuilder countJpql = new StringBuilder("SELECT count(d) FROM Deal d WHERE d.isActive = true");
        Map<String, Object> params = new HashMap<>();
        if (filter.getDealId() != null) {
            jpql.append(" AND d.id = :dealId");
            countJpql.append(" AND d.id = :dealId");
            params.put("dealId", filter.getDealId());
        }
        if (filter.getDescription() != null) {
            jpql.append(" AND d.description = :desc");
            countJpql.append(" AND d.description = :desc");
            params.put("desc", filter.getDescription());
        }
        if (filter.getAgreementNumber() != null) {
            jpql.append(" AND d.agreementNumber LIKE :agrNum");
            countJpql.append(" AND d.agreementNumber LIKE :agrNum");
            params.put("agrNum", "%" + filter.getAgreementNumber() + "%");
        }
        if (filter.getAgreementDateFrom() != null) {
            jpql.append(" AND d.agreementDate >= :agreementDateFrom");
            countJpql.append(" AND d.agreementDate >= :agreementDateFrom");
            params.put("agreementDateFrom", filter.getAgreementDateFrom());
        }
        if (filter.getAgreementDateTo() != null) {
            jpql.append(" AND d.agreementDate <= :agreementDateTo");
            countJpql.append(" AND d.agreementDate <= :agreementDateTo");
            params.put("agreementDateTo", filter.getAgreementDateTo());
        }
        if (filter.getTypeIds() != null && !filter.getTypeIds().isEmpty()) {
            jpql.append(" AND d.type.id IN :typeIds");
            countJpql.append(" AND d.type.id IN :typeIds");
            params.put("typeIds", filter.getTypeIds());
        }
        if (filter.getStatusIds() != null && !filter.getStatusIds().isEmpty()) {
            jpql.append(" AND d.status.id IN :statusIds");
            countJpql.append(" AND d.status.id IN :statusIds");
            params.put("statusIds", filter.getStatusIds());
        }
        if (filter.getSumValue() != null || filter.getSumCurrency() != null) {
            jpql.append(" AND EXISTS (SELECT s FROM DealSum s WHERE s.deal = d");
            countJpql.append(" AND EXISTS (SELECT s FROM DealSum s WHERE s.deal = d");
            if (filter.getSumValue() != null) {
                jpql.append(" AND s.value = :sumVal");
                countJpql.append(" AND s.value = :sumVal");
                params.put("sumVal", filter.getSumValue());
            }
            if (filter.getSumCurrency() != null) {
                jpql.append(" AND s.currency.id = :sumCur");
                countJpql.append(" AND s.currency.id = :sumCur");
                params.put("sumCur", filter.getSumCurrency());
            }
            jpql.append(")");
            countJpql.append(")");
        }
        if (filter.getBorrowerSearch() != null) {
            jpql.append(" AND EXISTS (SELECT dc FROM DealContractor dc JOIN ContractorToRole ctr ON ctr.dealContractor = dc WHERE dc.deal = d AND ctr.role.id = 'BORROWER' AND ctr.isActive = true AND (");
            countJpql.append(" AND EXISTS (SELECT dc FROM DealContractor dc JOIN ContractorToRole ctr ON ctr.dealContractor = dc WHERE dc.deal = d AND ctr.role.id = 'BORROWER' AND ctr.isActive = true AND (");
            jpql.append("dc.contractorId LIKE :borrower OR dc.name LIKE :borrower OR dc.inn LIKE :borrower))");
            countJpql.append("dc.contractorId LIKE :borrower OR dc.name LIKE :borrower OR dc.inn LIKE :borrower))");
            params.put("borrower", "%" + filter.getBorrowerSearch() + "%");
        }
        if (filter.getWarrantySearch() != null) {
            jpql.append(" AND EXISTS (SELECT dc FROM DealContractor dc JOIN ContractorToRole ctr ON ctr.dealContractor = dc WHERE dc.deal = d AND ctr.role.id = 'WARRANITY' AND ctr.isActive = true AND (");
            countJpql.append(" AND EXISTS (SELECT dc FROM DealContractor dc JOIN ContractorToRole ctr ON ctr.dealContractor = dc WHERE dc.deal = d AND ctr.role.id = 'WARRANITY' AND ctr.isActive = true AND (");
            jpql.append("dc.contractorId LIKE :warr OR dc.name LIKE :warr OR dc.inn LIKE :warr))");
            countJpql.append("dc.contractorId LIKE :warr OR dc.name LIKE :warr OR dc.inn LIKE :warr))");
            params.put("warr", "%" + filter.getWarrantySearch() + "%");
        }
        String sortField = filter.getSortField() != null ? filter.getSortField() : "agreementDate";
        String sortDir = filter.getSortDir() != null ? filter.getSortDir() : "DESC";
        jpql.append(" ORDER BY d." + sortField + " " + sortDir);
        TypedQuery<Deal> query = entityManager.createQuery(jpql.toString(), Deal.class);
        params.forEach(query::setParameter);
        query.setFirstResult(filter.getPage() * filter.getSize());
        query.setMaxResults(filter.getSize());
        List<Deal> deals = query.getResultList();
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);
        params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();
        return new PageImpl<>(deals, PageRequest.of(filter.getPage(), filter.getSize()), total);
    }

}
