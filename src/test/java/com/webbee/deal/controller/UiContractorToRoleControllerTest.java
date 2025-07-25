
package com.webbee.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbee.deal.TestSecurityConfig;
import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.security.service.AuthorizationService;
import com.webbee.deal.service.ContractorToRoleService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UiContractorToRoleController.class)
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
class UiContractorToRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContractorToRoleService contractorToRoleService;

    @Test
    @DisplayName("POST /ui/contractor-to-role/add - успешно добавляет роль контрагенту при наличии прав")
    @WithMockUser(username = "testuser", roles = {"DEAL_SUPERUSER"})
    void addContractorRole_WithAdminPermission_ReturnsCreated() throws Exception {

        UUID contractorId = UUID.randomUUID();
        String roleId = "ROLE001";
        ContractorToRoleIdDto idDto = new ContractorToRoleIdDto();
        idDto.setContractorId(contractorId);
        idDto.setRoleId(roleId);
        ContractorToRoleDto inputDto = new ContractorToRoleDto();
        inputDto.setId(idDto);
        ContractorToRoleDto resultDto = new ContractorToRoleDto();
        resultDto.setId(idDto);
        when(contractorToRoleService.addRoleToDealContractor(eq(contractorId), eq(roleId)))
                .thenReturn(resultDto);
        mockMvc.perform(post("/ui/contractor-to-role/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.contractorId").value(contractorId.toString()))
                .andExpect(jsonPath("$.id.roleId").value(roleId));
        verify(contractorToRoleService).addRoleToDealContractor(eq(contractorId), eq(roleId));

    }

    @Test
    @DisplayName("POST /ui/contractor-to-role/add - запрещает доступ пользователю без нужной роли")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void addContractorRole_WithoutAdminPermission_ReturnsForbidden() throws Exception {

        UUID contractorId = UUID.randomUUID();
        String roleId = "ROLE001";
        ContractorToRoleIdDto idDto = new ContractorToRoleIdDto();
        idDto.setContractorId(contractorId);
        idDto.setRoleId(roleId);
        ContractorToRoleDto inputDto = new ContractorToRoleDto();
        inputDto.setId(idDto);
        mockMvc.perform(post("/ui/contractor-to-role/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isForbidden());
        verifyNoInteractions(contractorToRoleService);

    }

    @Test
    @DisplayName("POST /ui/contractor-to-role/add - запрещает доступ неавторизованному пользователю")
    void addContractorRole_Unauthorized_ReturnsUnauthorized() throws Exception {

        UUID contractorId = UUID.randomUUID();
        String roleId = "ROLE001";
        ContractorToRoleIdDto idDto = new ContractorToRoleIdDto();
        idDto.setContractorId(contractorId);
        idDto.setRoleId(roleId);
        ContractorToRoleDto inputDto = new ContractorToRoleDto();
        inputDto.setId(idDto);
        mockMvc.perform(post("/ui/contractor-to-role/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isForbidden());
        verifyNoInteractions(contractorToRoleService);

    }

    @Test
    @DisplayName("DELETE /ui/contractor-to-role/delete - успешно удаляет связь при наличии прав")
    @WithMockUser(username = "testuser", roles = {"SUPERUSER"})
    void deleteContractorToRole_WithAdminPermission_ReturnsNoContent() throws Exception {

        UUID contractorId = UUID.randomUUID();
        String roleId = "ROLE001";
        doNothing().when(contractorToRoleService).deleteContractorToRole(any(UUID.class), anyString());
        mockMvc.perform(delete("/ui/contractor-to-role/delete")
                .with(csrf())
                .param("contractorId", contractorId.toString())
                .param("roleId", roleId))
                .andExpect(status().isNoContent());
        verify(contractorToRoleService).deleteContractorToRole(eq(contractorId), eq(roleId));

    }

    @Test
    @DisplayName("DELETE /ui/contractor-to-role/delete - запрещает доступ пользователю без нужной роли")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void deleteContractorToRole_WithoutAdminPermission_ReturnsForbidden() throws Exception {

        UUID contractorId = UUID.randomUUID();
        String roleId = "ROLE001";
        mockMvc.perform(delete("/ui/contractor-to-role/delete")
                .with(csrf())
                .param("contractorId", contractorId.toString())
                .param("roleId", roleId))
                .andExpect(status().isForbidden());
        verifyNoInteractions(contractorToRoleService);

    }

    @Test
    @DisplayName("DELETE /ui/contractor-to-role/delete - запрещает доступ неавторизованному пользователю")
    void deleteContractorToRole_Unauthorized_ReturnsUnauthorized() throws Exception {

        UUID contractorId = UUID.randomUUID();
        String roleId = "ROLE001";
        mockMvc.perform(delete("/ui/contractor-to-role/delete")
                .with(csrf())
                .param("contractorId", contractorId.toString())
                .param("roleId", roleId))
                .andExpect(status().isForbidden());
        verifyNoInteractions(contractorToRoleService);

    }

}