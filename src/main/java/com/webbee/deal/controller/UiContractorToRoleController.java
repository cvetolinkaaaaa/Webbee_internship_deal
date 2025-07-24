package com.webbee.deal.controller;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.service.ContractorToRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * UI контроллер для управления связями между контрагентами и ролями (требует авторизации).
 */
@RestController
@RequestMapping("/ui/contractor-to-role")
@RequiredArgsConstructor
@Tag(name = "UI Связи контрагентов и ролей", description = "UI операции для управления связями между контрагентами и ролями (требуют авторизации)")
@SecurityRequirement(name = "bearerAuth")
public class UiContractorToRoleController {

    private final ContractorToRoleService contractorToRoleService;

    /**
     * Добавить роль контрагенту.
     */
    @Operation(
            summary = "Добавить роль контрагенту",
            description = "Добавляет новую связь между контрагентом и ролью (требует авторизации)."
    )
    @ApiResponse(responseCode = "200", description = "Связь успешно создана")
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ContractorToRoleDto> addContractorRole(
            @RequestBody ContractorToRoleDto contractorToRoleDto
    ) {

        ContractorToRoleIdDto id = contractorToRoleDto.getId();

        ContractorToRoleDto result = contractorToRoleService.addRoleToDealContractor(
                id.getContractorId(),
                id.getRoleId()
        );
        return ResponseEntity.ok(result);

    }

    /**
     * Удалить связь между контрагентом и ролью.
     */
    @Operation(
            summary = "Удалить связь между контрагентом и ролью",
            description = "Удаляет существующую связь между контрагентом и ролью по их идентификаторам (требует авторизации)."
    )
    @ApiResponse(responseCode = "204", description = "Связь успешно удалена")
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteContractorToRole(
            @RequestParam("contractorId") UUID contractorId,
            @RequestParam("roleId") String roleId
    ) {

        contractorToRoleService.deleteContractorToRole(contractorId, roleId);
        return ResponseEntity.noContent().build();
    }

}
