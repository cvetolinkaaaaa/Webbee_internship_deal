package com.webbee.deal.repository;

import com.webbee.deal.entity.DealStatus;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Testcontainers
@ExtendWith(SpringExtension.class)
public class DealStatusRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);
    }

    @Autowired
    private DealStatusRepository dealStatusRepository;

    @BeforeEach
    void clean() {
        dealStatusRepository.deleteAll();
    }

    @Test
    void whenSave_thenFindByIdReturnsExpected() {
        DealStatus status = new DealStatus("NEW", "Новая сделка", true);
        dealStatusRepository.save(status);

        Optional<DealStatus> found = dealStatusRepository.findById("NEW");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Новая сделка");
        assertThat(found.get().getIsActive()).isTrue();
    }

    @Test
    void whenFindByNonExistentId_thenEmptyOptional() {
        Optional<DealStatus> found = dealStatusRepository.findById("NOT_EXISTS");
        assertThat(found).isNotPresent();
    }

    @Test
    void whenSaveMultiple_thenFindAllReturnsAll() {
        DealStatus s1 = new DealStatus("NEW", "Новая", true);
        DealStatus s2 = new DealStatus("IN_PROGRESS", "В работе", true);
        DealStatus s3 = new DealStatus("CLOSED", "Закрыта", false);

        dealStatusRepository.saveAll(List.of(s1, s2, s3));

        List<DealStatus> all = dealStatusRepository.findAll();
        assertThat(all).hasSize(3);
        assertThat(all)
                .extracting(DealStatus::getId)
                .containsExactlyInAnyOrder("NEW", "IN_PROGRESS", "CLOSED");
    }
}
