package com.webbee.deal.controller;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.service.ContractorToRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contractor-to-role")
public class ContractorToRoleController {

    private final ContractorToRoleService contractorToRoleService;

    public ContractorToRoleController(ContractorToRoleService contractorToRoleService) {
        this.contractorToRoleService = contractorToRoleService;
    }

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

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteContractorToRole(
            @RequestParam("contractorId") UUID contractorId,
            @RequestParam("roleId") String roleId
    ) {
        contractorToRoleService.deleteContractorToRole(contractorId, roleId);
        return ResponseEntity.noContent().build();
    }

}
