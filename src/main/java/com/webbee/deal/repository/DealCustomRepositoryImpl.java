package com.webbee.deal.repository;

import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.entity.DealSum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Deal> cq = cb.createQuery(Deal.class);
        Root<Deal> root = cq.from(Deal.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get("isActive")));

        if (filter.getDealId() != null) {
            predicates.add(cb.equal(root.get("id"), filter.getDealId()));
        }
        if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
            predicates.add(cb.equal(root.get("description"), filter.getDescription()));
        }
        if (filter.getAgreementNumber() != null && !filter.getAgreementNumber().isEmpty()) {
            predicates.add(cb.like(root.get("agreementNumber"), "%" + filter.getAgreementNumber() + "%"));
        }
        if (filter.getAgreementDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("agreementDate"), filter.getAgreementDateFrom()));
        }
        if (filter.getAgreementDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("agreementDate"), filter.getAgreementDateTo()));
        }
        if (filter.getAvailabilityDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("availabilityDate"), filter.getAvailabilityDateFrom()));
        }
        if (filter.getAvailabilityDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("availabilityDate"), filter.getAvailabilityDateTo()));
        }
        if (filter.getTypeIds() != null && !filter.getTypeIds().isEmpty()) {
            predicates.add(root.get("type").get("id").in(filter.getTypeIds()));
        }
        if (filter.getStatusIds() != null && !filter.getStatusIds().isEmpty()) {
            predicates.add(root.get("status").get("id").in(filter.getStatusIds()));
        }
        if (filter.getCloseDtFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("closeDt"), filter.getCloseDtFrom().atStartOfDay()));
        }
        if (filter.getCloseDtTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("closeDt"), filter.getCloseDtTo().atTime(23, 59, 59)));
        }

        if (filter.getSumValue() != null || filter.getSumCurrency() != null) {
            Subquery<Long> sumSubquery = cq.subquery(Long.class);
            Root<DealSum> sumRoot = sumSubquery.from(DealSum.class);
            List<Predicate> sumPredicates = new ArrayList<>();
            sumPredicates.add(cb.equal(sumRoot.get("deal").get("id"), root.get("id")));
            if (filter.getSumValue() != null) {
                sumPredicates.add(cb.equal(sumRoot.get("value"), new java.math.BigDecimal(filter.getSumValue())));
            }
            if (filter.getSumCurrency() != null) {
                sumPredicates.add(cb.equal(sumRoot.get("currency").get("id"), filter.getSumCurrency()));
            }
            sumSubquery.select(sumRoot.get("id"))
                    .where(sumPredicates.toArray(new Predicate[0]));
            predicates.add(cb.exists(sumSubquery));
        }

        if (filter.getBorrowerSearch() != null && !filter.getBorrowerSearch().isEmpty()) {
            Subquery<UUID> borrowerSubquery = cq.subquery(UUID.class);
            Root<DealContractor> dcRoot = borrowerSubquery.from(DealContractor.class);
            Join<DealContractor, ContractorToRole> ctrJoin = dcRoot.join("contractorToRoles");
            List<Predicate> subPredicates = new ArrayList<>();
            subPredicates.add(cb.equal(dcRoot.get("deal").get("id"), root.get("id")));
            subPredicates.add(cb.equal(ctrJoin.get("role").get("id"), "BORROWER"));
            subPredicates.add(cb.isTrue(ctrJoin.get("isActive")));
            String pattern = "%" + filter.getBorrowerSearch() + "%";
            Predicate byContractor = cb.like(dcRoot.get("contractorId"), pattern);
            Predicate byName = cb.like(dcRoot.get("name"), pattern);
            Predicate byInn = cb.like(dcRoot.get("inn"), pattern);
            subPredicates.add(cb.or(byContractor, byName, byInn));
            borrowerSubquery
                    .select(dcRoot.get("id"))
                    .where(subPredicates.toArray(new Predicate[0]));
            predicates.add(cb.exists(borrowerSubquery));
        }

        if (filter.getWarrantySearch() != null && !filter.getWarrantySearch().isEmpty()) {
            Subquery<UUID> warrantySubquery = cq.subquery(UUID.class);
            Root<DealContractor> dcRoot = warrantySubquery.from(DealContractor.class);
            Join<DealContractor, ContractorToRole> ctrJoin = dcRoot.join("contractorToRoles");
            List<Predicate> subPredicates = new ArrayList<>();
            subPredicates.add(cb.equal(dcRoot.get("deal").get("id"), root.get("id")));
            subPredicates.add(cb.equal(ctrJoin.get("role").get("id"), "WARRANITY"));
            subPredicates.add(cb.isTrue(ctrJoin.get("isActive")));
            String pattern = "%" + filter.getWarrantySearch() + "%";
            Predicate byContractor = cb.like(dcRoot.get("contractorId"), pattern);
            Predicate byName = cb.like(dcRoot.get("name"), pattern);
            Predicate byInn = cb.like(dcRoot.get("inn"), pattern);
            subPredicates.add(cb.or(byContractor, byName, byInn));
            warrantySubquery
                    .select(dcRoot.get("id"))
                    .where(subPredicates.toArray(new Predicate[0]));
            predicates.add(cb.exists(warrantySubquery));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        String sortField = filter.getSortField() != null ? filter.getSortField() : "agreementDate";
        String sortDir = filter.getSortDir() != null ? filter.getSortDir() : "DESC";
        if ("DESC".equalsIgnoreCase(sortDir)) {
            cq.orderBy(cb.desc(root.get(sortField)));
        } else {
            cq.orderBy(cb.asc(root.get(sortField)));
        }

        TypedQuery<Deal> query = entityManager.createQuery(cq);
        query.setFirstResult(filter.getPage() * filter.getSize());
        query.setMaxResults(filter.getSize());
        List<Deal> deals = query.getResultList();

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<Deal> countRoot = countCq.from(Deal.class);
        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.isTrue(countRoot.get("isActive")));
        if (filter.getDealId() != null) {
            countPredicates.add(cb.equal(countRoot.get("id"), filter.getDealId()));
        }
        if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
            countPredicates.add(cb.equal(countRoot.get("description"), filter.getDescription()));
        }
        if (filter.getAgreementNumber() != null && !filter.getAgreementNumber().isEmpty()) {
            countPredicates.add(cb.like(countRoot.get("agreementNumber"), "%" + filter.getAgreementNumber() + "%"));
        }
        if (filter.getAgreementDateFrom() != null) {
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("agreementDate"), filter.getAgreementDateFrom()));
        }
        if (filter.getAgreementDateTo() != null) {
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("agreementDate"), filter.getAgreementDateTo()));
        }
        if (filter.getAvailabilityDateFrom() != null) {
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("availabilityDate"), filter.getAvailabilityDateFrom()));
        }
        if (filter.getAvailabilityDateTo() != null) {
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("availabilityDate"), filter.getAvailabilityDateTo()));
        }
        if (filter.getTypeIds() != null && !filter.getTypeIds().isEmpty()) {
            countPredicates.add(countRoot.get("type").get("id").in(filter.getTypeIds()));
        }
        if (filter.getStatusIds() != null && !filter.getStatusIds().isEmpty()) {
            countPredicates.add(countRoot.get("status").get("id").in(filter.getStatusIds()));
        }
        if (filter.getCloseDtFrom() != null) {
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("closeDt"), filter.getCloseDtFrom().atStartOfDay()));
        }
        if (filter.getCloseDtTo() != null) {
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("closeDt"), filter.getCloseDtTo().atTime(23, 59, 59)));
        }
        if (filter.getSumValue() != null || filter.getSumCurrency() != null) {
            Subquery<Long> sumSubquery = countCq.subquery(Long.class);
            Root<DealSum> sumRoot = sumSubquery.from(DealSum.class);
            List<Predicate> sumPredicates = new ArrayList<>();
            sumPredicates.add(cb.equal(sumRoot.get("deal").get("id"), countRoot.get("id")));
            if (filter.getSumValue() != null) {
                sumPredicates.add(cb.equal(sumRoot.get("value"), new java.math.BigDecimal(filter.getSumValue())));
            }
            if (filter.getSumCurrency() != null) {
                sumPredicates.add(cb.equal(sumRoot.get("currency").get("id"), filter.getSumCurrency()));
            }
            sumSubquery.select(sumRoot.get("id"))
                    .where(sumPredicates.toArray(new Predicate[0]));
            countPredicates.add(cb.exists(sumSubquery));
        }
        if (filter.getBorrowerSearch() != null && !filter.getBorrowerSearch().isEmpty()) {
            Subquery<UUID> borrowerSubquery = countCq.subquery(UUID.class);
            Root<DealContractor> dcRoot = borrowerSubquery.from(DealContractor.class);
            Join<DealContractor, ContractorToRole> ctrJoin = dcRoot.join("contractorToRoles");
            List<Predicate> subPredicates = new ArrayList<>();
            subPredicates.add(cb.equal(dcRoot.get("deal").get("id"), countRoot.get("id")));
            subPredicates.add(cb.equal(ctrJoin.get("role").get("id"), "BORROWER"));
            subPredicates.add(cb.isTrue(ctrJoin.get("isActive")));
            String pattern = "%" + filter.getBorrowerSearch() + "%";
            Predicate byContractor = cb.like(dcRoot.get("contractorId"), pattern);
            Predicate byName = cb.like(dcRoot.get("name"), pattern);
            Predicate byInn = cb.like(dcRoot.get("inn"), pattern);
            subPredicates.add(cb.or(byContractor, byName, byInn));
            borrowerSubquery
                    .select(dcRoot.get("id"))
                    .where(subPredicates.toArray(new Predicate[0]));
            countPredicates.add(cb.exists(borrowerSubquery));
        }
        if (filter.getWarrantySearch() != null && !filter.getWarrantySearch().isEmpty()) {
            Subquery<UUID> warrantySubquery = countCq.subquery(UUID.class);
            Root<DealContractor> dcRoot = warrantySubquery.from(DealContractor.class);
            Join<DealContractor, ContractorToRole> ctrJoin = dcRoot.join("contractorToRoles");
            List<Predicate> subPredicates = new ArrayList<>();
            subPredicates.add(cb.equal(dcRoot.get("deal").get("id"), countRoot.get("id")));
            subPredicates.add(cb.equal(ctrJoin.get("role").get("id"), "WARRANITY"));
            subPredicates.add(cb.isTrue(ctrJoin.get("isActive")));
            String pattern = "%" + filter.getWarrantySearch() + "%";
            Predicate byContractor = cb.like(dcRoot.get("contractorId"), pattern);
            Predicate byName = cb.like(dcRoot.get("name"), pattern);
            Predicate byInn = cb.like(dcRoot.get("inn"), pattern);
            subPredicates.add(cb.or(byContractor, byName, byInn));
            warrantySubquery
                    .select(dcRoot.get("id"))
                    .where(subPredicates.toArray(new Predicate[0]));
            countPredicates.add(cb.exists(warrantySubquery));
        }
        countCq.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countCq).getSingleResult();

        return new PageImpl<>(deals, PageRequest.of(filter.getPage(), filter.getSize()), total);
    }

}
