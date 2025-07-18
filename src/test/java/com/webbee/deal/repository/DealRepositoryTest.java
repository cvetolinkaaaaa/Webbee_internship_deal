package com.webbee.deal.repository;

import com.webbee.deal.entity.Deal;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
@ExtendWith(SpringExtension.class)
public class DealRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);
    }

    @Autowired
    private DealRepository dealRepository;

    @BeforeEach
    void setUp() {
        dealRepository.deleteAll();
    }

    @Test
    void whenSave_thenFindByIdReturnsDeal() {
        Deal deal = new Deal();
        // set поля у deal, например:
        // deal.setName("Test deal");
        dealRepository.save(deal);

        Optional<Deal> found = dealRepository.findById(deal.getId());
        assertThat(found).isPresent();
    }
}
