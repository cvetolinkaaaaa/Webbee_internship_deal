package com.webbee.deal.controller;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.service.ContractorToRoleService;
import com.webbee.deal.service.DealContractorService;
import com.webbee.deal.service.DealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/deal-contractor")
public class DealContractorController {

    private final DealContractorService dealContractorService;
    private final ContractorToRoleService contractorToRoleService;

    public DealContractorController(DealContractorService dealContractorService, ContractorToRoleService contractorToRoleService) {
        this.dealContractorService = dealContractorService;
        this.contractorToRoleService = contractorToRoleService;
    }

    @PutMapping("/save")
    public ResponseEntity<?> saveDealContractor(@RequestBody DealContractorDto dto) {
        dealContractorService.saveDealContractor(dto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDealContractor(
            @RequestParam("id") UUID dealContractorId
    ) {
        dealContractorService.deleteDealContractor(dealContractorId);
        return ResponseEntity.noContent().build();
    }

}
