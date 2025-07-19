package com.webbee.deal.controller;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.service.ContractorToRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Контроллер для управления связями между контрагентами и ролями.
 */
@RestController
@RequestMapping("/contractor-to-role")
@Tag(name = "Связи контрагентов и ролей", description = "Операции для управления связями между контрагентами и ролями")
public class ContractorToRoleController {

    private final ContractorToRoleService contractorToRoleService;

    public ContractorToRoleController(ContractorToRoleService contractorToRoleService) {
        this.contractorToRoleService = contractorToRoleService;
    }

    /**
     * Добавить роль контрагенту.
     */
    @Operation(
            summary = "Добавить роль контрагенту",
            description = "Добавляет новую связь между контрагентом и ролью."
    )
    @ApiResponse(responseCode = "200", description = "Связь успешно создана")
    @PostMapping("/add")
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
            description = "Удаляет существующую связь между контрагентом и ролью по их идентификаторам."
    )
    @ApiResponse(responseCode = "204", description = "Связь успешно удалена")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteContractorToRole(
            @RequestParam("contractorId") UUID contractorId,
            @RequestParam("roleId") String roleId
    ) {
        contractorToRoleService.deleteContractorToRole(contractorId, roleId);
        return ResponseEntity.noContent().build();
    }

}
