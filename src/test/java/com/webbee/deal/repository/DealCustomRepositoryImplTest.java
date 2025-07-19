package com.webbee.deal.repository;

import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.entity.DealType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ExtendWith(SpringExtension.class)
class DealCustomRepositoryImplTest {

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
    private DealRepository dealRepository;

    @Qualifier("dealCustomRepositoryImpl")
    @Autowired
    private DealCustomRepository dealCustomRepository;

    @Autowired
    private DealTypeRepository dealTypeRepository;

    @Autowired
    private DealStatusRepository dealStatusRepository;

    @BeforeEach
    void setUp() {
        dealRepository.deleteAll();
        dealTypeRepository.deleteAll();
        dealStatusRepository.deleteAll();
    }

    @Test
    void searchDeals_withAgreementNumber_shouldReturnCorrectDeal() {
        DealStatus status = new DealStatus("ACTIVE", "Активный", true);
        status = dealStatusRepository.save(status);

        DealType type = new DealType("LEASE", "Лизинг", true);
        type = dealTypeRepository.save(type);

        Deal deal = new Deal();
        deal.setAgreementNumber("AGREEMENT-12345");
        deal.setStatus(status);
        deal.setType(type);
        dealRepository.save(deal);

        DealSearchRequest request = new DealSearchRequest();
        request.setAgreementNumber("AGREEMENT-12345");
        request.setPage(0);
        request.setSize(10);

        Page<Deal> result = dealCustomRepository.searchDeals(request);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getAgreementNumber()).isEqualTo("AGREEMENT-12345");
    }

}