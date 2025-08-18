
package com.webbee.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.service.DealContractorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DealContractorController.class)
class DealContractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DealContractorService dealContractorService;

    @Test
    void saveDealContractor_Success() throws Exception {
        DealContractorDto dto = new DealContractorDto();
        dto.setContractorId("123456789012");
        dto.setName("Test Contractor");
        dto.setIsMain(true);

        doNothing().when(dealContractorService).saveDealContractor(any(DealContractorDto.class));

        mockMvc.perform(put("/deal-contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(dealContractorService).saveDealContractor(any(DealContractorDto.class));
    }

    @Test
    void saveDealContractor_BadRequest() throws Exception {
        DealContractorDto dto = new DealContractorDto();

        doThrow(new IllegalArgumentException("Invalid data"))
                .when(dealContractorService).saveDealContractor(any(DealContractorDto.class));

        mockMvc.perform(put("/deal-contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteDealContractor_Success() throws Exception {
        UUID dealContractorId = UUID.randomUUID();

        doNothing().when(dealContractorService).deleteDealContractor(dealContractorId);

        mockMvc.perform(delete("/deal-contractor/delete")
                        .param("id", dealContractorId.toString()))
                .andExpect(status().isNoContent());

        verify(dealContractorService).deleteDealContractor(dealContractorId);
    }

    @Test
    void deleteDealContractor_NotFound() throws Exception {
        UUID dealContractorId = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Deal contractor not found"))
                .when(dealContractorService).deleteDealContractor(dealContractorId);

        mockMvc.perform(delete("/deal-contractor/delete")
                        .param("id", dealContractorId.toString()))
                .andExpect(status().isNotFound());
    }
}