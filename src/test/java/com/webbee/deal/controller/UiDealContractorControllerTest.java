package com.webbee.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.TestSecurityConfig;
import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.service.DealContractorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UiDealContractorController.class)
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration," +
    "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
    "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
    "jwt.secret=TestSecret",
    "spring.liquibase.enabled=false",
    "spring.jpa.hibernate.ddl-auto=none"
})
@Import(TestSecurityConfig.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UiDealContractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DealContractorService dealContractorService;

    @Test
    @DisplayName("PUT /ui/deal-contractor/save - успешно сохраняет связь при наличии прав")
    @WithMockUser(username = "testuser", roles = {"DEAL_SUPERUSER"})
    void saveDealContractor_WithAdminPermission_ReturnsCreated() throws Exception {

        DealContractorDto dto = new DealContractorDto();
        dto.setId(UUID.randomUUID());
        doNothing().when(dealContractorService).saveDealContractor(any(DealContractorDto.class));
        mockMvc.perform(put("/ui/deal-contractor/save")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
        verify(dealContractorService).saveDealContractor(any(DealContractorDto.class));

    }

    @Test
    @DisplayName("PUT /ui/deal-contractor/save - запрещает доступ пользователю без нужной роли")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void saveDealContractor_WithoutAdminPermission_ReturnsForbidden() throws Exception {

        DealContractorDto dto = new DealContractorDto();
        dto.setId(UUID.randomUUID());
        mockMvc.perform(put("/ui/deal-contractor/save")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
        verifyNoInteractions(dealContractorService);

    }

    @Test
    @DisplayName("PUT /ui/deal-contractor/save - запрещает доступ неавторизованному пользователю")
    void saveDealContractor_Unauthorized_ReturnsForbidden() throws Exception {

        DealContractorDto dto = new DealContractorDto();
        dto.setId(UUID.randomUUID());
        mockMvc.perform(put("/ui/deal-contractor/save")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
        verifyNoInteractions(dealContractorService);

    }

    @Test
    @DisplayName("DELETE /ui/deal-contractor/delete - успешно удаляет связь при наличии прав")
    @WithMockUser(username = "testuser", roles = {"SUPERUSER"})
    void deleteDealContractor_WithAdminPermission_ReturnsNoContent() throws Exception {

        UUID dealContractorId = UUID.randomUUID();
        doNothing().when(dealContractorService).deleteDealContractor(any(UUID.class));
        mockMvc.perform(delete("/ui/deal-contractor/delete")
                .with(csrf())
                .param("id", dealContractorId.toString()))
                .andExpect(status().isNoContent());
        verify(dealContractorService).deleteDealContractor(eq(dealContractorId));

    }

    @Test
    @DisplayName("DELETE /ui/deal-contractor/delete - запрещает доступ пользователю без нужной роли")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void deleteDealContractor_WithoutAdminPermission_ReturnsForbidden() throws Exception {

        UUID dealContractorId = UUID.randomUUID();
        mockMvc.perform(delete("/ui/deal-contractor/delete")
                .with(csrf())
                .param("id", dealContractorId.toString()))
                .andExpect(status().isForbidden());
        verifyNoInteractions(dealContractorService);

    }

    @Test
    @DisplayName("DELETE /ui/deal-contractor/delete - запрещает доступ неавторизованному пользователю")
    void deleteDealContractor_Unauthorized_ReturnsForbidden() throws Exception {

        UUID dealContractorId = UUID.randomUUID();
        mockMvc.perform(delete("/ui/deal-contractor/delete")
                .with(csrf())
                .param("id", dealContractorId.toString()))
                .andExpect(status().isForbidden());
        verifyNoInteractions(dealContractorService);

    }

}