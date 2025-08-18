package com.webbee.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.service.ContractorToRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContractorToRoleController.class)
class ContractorToRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContractorToRoleService contractorToRoleService;

    @Test
    void addRoleToContractor_Success() throws Exception {
        UUID contractorId = UUID.randomUUID();
        ContractorToRoleIdDto idDto = new ContractorToRoleIdDto();
        idDto.setContractorId(contractorId);
        idDto.setRoleId("BORROWER");

        ContractorToRoleDto dto = new ContractorToRoleDto();
        dto.setId(idDto);

        ContractorToRoleDto savedDto = new ContractorToRoleDto();
        savedDto.setId(idDto);

        when(contractorToRoleService.addRoleToDealContractor(eq(contractorId), eq("BORROWER")))
                .thenReturn(savedDto);

        mockMvc.perform(post("/contractor-to-role/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.roleId").value("BORROWER"));

        verify(contractorToRoleService).addRoleToDealContractor(contractorId, "BORROWER");
    }

    @Test
    void addRoleToContractor_BadRequest() throws Exception {
        UUID contractorId = UUID.randomUUID();
        ContractorToRoleIdDto idDto = new ContractorToRoleIdDto();
        idDto.setContractorId(contractorId);
        idDto.setRoleId("BORROWER");

        ContractorToRoleDto dto = new ContractorToRoleDto();
        dto.setId(idDto);

        when(contractorToRoleService.addRoleToDealContractor(eq(contractorId), eq("BORROWER")))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        mockMvc.perform(post("/contractor-to-role/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeRoleFromContractor_Success() throws Exception {
        UUID contractorId = UUID.randomUUID();
        String roleId = "BORROWER";

        doNothing().when(contractorToRoleService).deleteContractorToRole(contractorId, roleId);

        mockMvc.perform(delete("/contractor-to-role/delete")
                        .param("contractorId", contractorId.toString())
                        .param("roleId", roleId))
                .andExpect(status().isNoContent());

        verify(contractorToRoleService).deleteContractorToRole(contractorId, roleId);
    }

    @Test
    void removeRoleFromContractor_NotFound() throws Exception {
        UUID contractorId = UUID.randomUUID();
        String roleId = "BORROWER";

        doThrow(new EntityNotFoundException("Role not found"))
                .when(contractorToRoleService).deleteContractorToRole(contractorId, roleId);

        mockMvc.perform(delete("/contractor-to-role/delete")
                        .param("contractorId", contractorId.toString())
                        .param("roleId", roleId))
                .andExpect(status().isNotFound());
    }
}