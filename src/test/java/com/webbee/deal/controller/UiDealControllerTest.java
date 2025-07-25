package com.webbee.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.TestSecurityConfig;
import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.dto.DealDto;
import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.dto.DealStatusChangeRequest;
import com.webbee.deal.security.service.AuthorizationService;
import com.webbee.deal.service.DealDetailsService;
import com.webbee.deal.service.DealExportService;
import com.webbee.deal.service.DealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UiDealController.class)
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
class UiDealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DealService dealService;

    @MockitoBean
    private DealDetailsService dealDetailsService;

    @MockitoBean
    private DealExportService dealExportService;

    @MockitoBean
    private AuthorizationService authorizationService;

    @Test
    @DisplayName("PUT /ui/deal/save - успешно сохраняет сделку при наличии прав")
    @WithMockUser(username = "testuser", roles = {"DEAL_SUPERUSER"})
    void saveDeal_WithAdminPermission_ReturnsCreated() throws Exception {

        DealDto dto = new DealDto();
        dto.setId(UUID.randomUUID());
        when(authorizationService.canModifyDeals()).thenReturn(true);
        when(authorizationService.isDealContractorAccessDenied()).thenReturn(false);
        doNothing().when(dealService).saveDealWithAuth(any(DealDto.class));
        mockMvc.perform(put("/ui/deal/save")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        verify(dealService).saveDealWithAuth(any(DealDto.class));
        verify(authorizationService).canModifyDeals();
        verify(authorizationService).isDealContractorAccessDenied();

    }

    @Test
    @DisplayName("PUT /ui/deal/save - запрещает доступ при отсутствии прав на модификацию")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void saveDeal_WithoutModifyPermission_ReturnsForbidden() throws Exception {

        DealDto dto = new DealDto();
        dto.setId(UUID.randomUUID());
        when(authorizationService.canModifyDeals()).thenReturn(false);
        mockMvc.perform(put("/ui/deal/save")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
        verify(authorizationService).canModifyDeals();
        verifyNoMoreInteractions(dealService);

    }

    @Test
    @DisplayName("PATCH /ui/deal/change/status - успешно изменяет статус сделки при наличии прав")
    @WithMockUser(username = "testuser", roles = {"DEAL_SUPERUSER"})
    void changeStatus_WithAdminPermission_ReturnsOk() throws Exception {

        UUID dealId = UUID.randomUUID();
        String statusId = "STATUS_ACTIVE";
        DealStatusChangeRequest request = new DealStatusChangeRequest();
        request.setDealId(dealId);
        request.setStatusId(statusId);
        when(authorizationService.canModifyDeals()).thenReturn(true);
        when(authorizationService.isDealContractorAccessDenied()).thenReturn(false);
        DealDto mockDealDto = new DealDto();
        when(dealService.changeDealStatus(eq(dealId), eq(statusId))).thenReturn(mockDealDto);
        mockMvc.perform(patch("/ui/deal/change/status")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        verify(dealService).changeDealStatus(eq(dealId), eq(statusId));
        verify(authorizationService).canModifyDeals();
        verify(authorizationService).isDealContractorAccessDenied();

    }

    @Test
    @DisplayName("POST /ui/deal/search - успешно выполняет поиск сделок при наличии прав")
    @WithMockUser(username = "testuser", roles = {"SUPERUSER"})
    void searchDeals_WithAccessPermission_ReturnsResults() throws Exception {

        DealSearchRequest request = new DealSearchRequest();
        DealDetailsDto detailsDto = new DealDetailsDto();
        detailsDto.setId(UUID.randomUUID());
        Page<DealDetailsDto> page = new PageImpl<>(Collections.singletonList(detailsDto));
        when(authorizationService.isDealContractorAccessDenied()).thenReturn(false);
        when(dealService.searchDealsWithAuth(any(DealSearchRequest.class))).thenReturn(page);
        mockMvc.perform(post("/ui/deal/search")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(detailsDto.getId().toString()));
        verify(dealService).searchDealsWithAuth(any(DealSearchRequest.class));
        verify(authorizationService).isDealContractorAccessDenied();

    }

    @Test
    @DisplayName("POST /ui/deal/search/export - успешно экспортирует сделки при наличии прав")
    @WithMockUser(username = "testuser", roles = {"SUPERUSER"})
    void exportDealsToExcel_WithAccessPermission_ReturnsExcelFile() throws Exception {

        DealSearchRequest request = new DealSearchRequest();
        byte[] excelBytes = "test excel content".getBytes();
        when(authorizationService.isDealContractorAccessDenied()).thenReturn(false);
        when(dealExportService.exportDealsToExcelWithAuth(any(DealSearchRequest.class))).thenReturn(excelBytes);
        mockMvc.perform(post("/ui/deal/search/export")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=deals_export.xlsx"))
                .andExpect(header().string("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(content().bytes(excelBytes));
        verify(dealExportService).exportDealsToExcelWithAuth(any(DealSearchRequest.class));
        verify(authorizationService).isDealContractorAccessDenied();

    }

    @Test
    @DisplayName("GET /ui/deal/{id} - успешно возвращает детали сделки при наличии прав")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void getDealDetails_WithViewPermission_ReturnsDealDetails() throws Exception {

        UUID dealId = UUID.randomUUID();
        DealDetailsDto detailsDto = new DealDetailsDto();
        detailsDto.setId(dealId);
        when(authorizationService.canViewReferenceData()).thenReturn(true);
        when(dealDetailsService.getDealDetails(eq(dealId))).thenReturn(detailsDto);
        mockMvc.perform(get("/ui/deal/" + dealId)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(dealId.toString()));
        verify(dealDetailsService).getDealDetails(eq(dealId));
        verify(authorizationService).canViewReferenceData();

    }

    @Test
    @DisplayName("GET /ui/deal/{id} - запрещает доступ при отсутствии прав на просмотр")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void getDealDetails_WithoutViewPermission_ReturnsForbidden() throws Exception {

        UUID dealId = UUID.randomUUID();
        when(authorizationService.canViewReferenceData()).thenReturn(false);
        mockMvc.perform(get("/ui/deal/" + dealId)
                .with(csrf()))
                .andExpect(status().isForbidden());
        verify(authorizationService).canViewReferenceData();
        verifyNoInteractions(dealDetailsService);

    }

}