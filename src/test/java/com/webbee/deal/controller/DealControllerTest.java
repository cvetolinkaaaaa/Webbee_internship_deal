package com.webbee.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.dto.*;
import com.webbee.deal.service.DealDetailsService;
import com.webbee.deal.service.DealExportService;
import com.webbee.deal.service.DealService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DealController.class)
class DealControllerTest {

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

    @Test
    void getDeal_Success() throws Exception {
        UUID dealId = UUID.randomUUID();
        DealDetailsDto dealDetailsDto = new DealDetailsDto();
        dealDetailsDto.setId(dealId);
        dealDetailsDto.setDescription("Test deal");

        when(dealDetailsService.getDealDetails(dealId)).thenReturn(dealDetailsDto);

        mockMvc.perform(get("/deal/{id}", dealId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dealId.toString()))
                .andExpect(jsonPath("$.description").value("Test deal"));
    }

    @Test
    void getDeal_NotFound() throws Exception {
        UUID dealId = UUID.randomUUID();
        when(dealDetailsService.getDealDetails(dealId))
                .thenThrow(new EntityNotFoundException("Deal not found"));

        mockMvc.perform(get("/deal/{id}", dealId))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveDeal_Success() throws Exception {
        DealDto dealDto = new DealDto();
        dealDto.setDescription("New deal");
        
        DealDto savedDeal = new DealDto();
        savedDeal.setId(UUID.randomUUID());
        savedDeal.setDescription("New deal");

        when(dealService.saveDeal(any(DealDto.class))).thenReturn(savedDeal);

        mockMvc.perform(put("/deal/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dealDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("New deal"));
    }

    @Test
    void changeDealStatus_Success() throws Exception {
        UUID dealId = UUID.randomUUID();
        DealStatusChangeRequest request = new DealStatusChangeRequest();
        request.setDealId(dealId);
        request.setStatusId("ACTIVE");

        DealDto updatedDeal = new DealDto();
        updatedDeal.setId(dealId);

        when(dealService.changeDealStatus(dealId, "ACTIVE")).thenReturn(updatedDeal);

        mockMvc.perform(patch("/deal/change/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dealId.toString()));
    }

    @Test
    void exportDeals_Success() throws Exception {
        DealSearchRequest searchRequest = new DealSearchRequest();
        byte[] excelData = "excel content".getBytes();

        when(dealExportService.exportDealsToExcel(any(DealSearchRequest.class))).thenReturn(excelData);

        mockMvc.perform(post("/deal/search/export")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(header().exists("Content-Disposition"));
    }
}