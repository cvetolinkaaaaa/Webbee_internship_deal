package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.mapper.DealContractorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DealContractorMapperTest {

    @Autowired
    private DealContractorMapper mapper;

    @Test
    void testToDto() {
        DealContractor entity = new DealContractor();
        entity.setId(UUID.randomUUID());
        entity.setContractorId("CONTR123");
        entity.setName("ООО Рога и Копыта");
        entity.setInn("7701234567");
        entity.setCreateUserId("user_1");
        entity.setCreateDate(LocalDateTime.now());

        // ... если есть вложенные объекты, заполни их аналогично

        DealContractorDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getContractorId()).isEqualTo(entity.getContractorId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getInn()).isEqualTo(entity.getInn());
        assertThat(dto.getCreateUserId()).isEqualTo(entity.getCreateUserId());
    }

    @Test
    void testToEntity() {
        DealContractorDto dto = new DealContractorDto();
        dto.setId(UUID.randomUUID());
        dto.setContractorId("CONTR124");
        dto.setName("ООО Альфа");
        dto.setInn("7709876543");
        dto.setCreateUserId("user_2");
        dto.setCreateDate(LocalDateTime.now());

        // ... если есть вложенные объекты, заполни их аналогично

        DealContractor entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getContractorId()).isEqualTo(dto.getContractorId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getInn()).isEqualTo(dto.getInn());
        assertThat(entity.getCreateUserId()).isEqualTo(dto.getCreateUserId());
    }
}
