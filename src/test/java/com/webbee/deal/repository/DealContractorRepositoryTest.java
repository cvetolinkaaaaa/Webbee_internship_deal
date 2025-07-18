package com.webbee.deal.repository;

import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.entity.DealType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ExtendWith(SpringExtension.class)
public class DealContractorRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);
    }

    @Autowired
    private DealContractorRepository dealContractorRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private DealTypeRepository dealTypeRepository;

    @Autowired
    private DealStatusRepository dealStatusRepository;

    private Deal testDeal;

    @BeforeEach
    void setUp() {
        dealContractorRepository.deleteAll();
        dealRepository.deleteAll();
        dealTypeRepository.deleteAll();

        DealType dealType = new DealType();
        dealType.setId("TYPE1");
        dealType.setName("Test Type");
        dealType.setIsActive(true);
        dealType = dealTypeRepository.save(dealType);

        DealStatus status = new DealStatus();
        status.setId("NEW");
        status.setName("Новый");
        status = dealStatusRepository.save(status);

        testDeal = new Deal();
        testDeal.setType(dealType);
        testDeal.setStatus(status);
        testDeal.setDescription("Test Deal");
        testDeal.setIsActive(true);
        testDeal.setCreateDate(LocalDateTime.now());
        dealRepository.save(testDeal);
    }

    @Test
    void testSaveAndFindById() {
        DealContractor contractor = new DealContractor();
        contractor.setDeal(testDeal);
        contractor.setContractorId("999");
        contractor.setName("Тестовый Контрагент");
        contractor.setIsActive(true);

        DealContractor saved = dealContractorRepository.save(contractor);
        assertThat(saved.getId()).isNotNull();

        DealContractor found = dealContractorRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getContractorId()).isEqualTo("999");
        assertThat(found.getDeal().getId()).isEqualTo(testDeal.getId());
    }

    @Test
    void testFindByDealIdAndIsActiveTrue_FiltersCorrectly() {
        DealType anotherType = new DealType();
        anotherType.setId("TYPE2");
        anotherType.setName("Other Type");
        anotherType.setIsActive(true);
        anotherType = dealTypeRepository.save(anotherType);

        DealStatus status = new DealStatus("NEW", "Новый", true);
        status = dealStatusRepository.save(status);

        Deal anotherDeal = new Deal();
        anotherDeal.setType(anotherType);
        anotherDeal.setStatus(status);
        anotherDeal.setDescription("Another Deal");
        anotherDeal.setIsActive(true);
        anotherDeal.setCreateDate(LocalDateTime.now());
        dealRepository.save(anotherDeal);

        DealContractor c1 = new DealContractor();
        c1.setDeal(testDeal);
        c1.setContractorId("111");
        c1.setName("Контрагент A");
        c1.setIsActive(true);

        DealContractor c2 = new DealContractor();
        c2.setDeal(testDeal);
        c2.setContractorId("222");
        c2.setName("Контрагент B");
        c2.setIsActive(false);

        DealContractor c3 = new DealContractor();
        c3.setDeal(testDeal);
        c3.setContractorId("333");
        c3.setName("Контрагент C");
        c3.setIsActive(true);

        DealContractor c4 = new DealContractor();
        c4.setDeal(anotherDeal);
        c4.setContractorId("444");
        c4.setName("Контрагент D");
        c4.setIsActive(true);

        dealContractorRepository.saveAll(List.of(c1, c2, c3, c4));

        List<DealContractor> activeForTestDeal = dealContractorRepository.findByDealIdAndIsActiveTrue(testDeal.getId());

        assertThat(activeForTestDeal)
                .hasSize(2)
                .extracting(DealContractor::getContractorId)
                .containsExactlyInAnyOrder("111", "333");
    }
}
