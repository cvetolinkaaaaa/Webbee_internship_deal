package com.webbee.deal.controller;

import com.webbee.deal.dto.*;
import com.webbee.deal.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;
    private final DealContractorService contractorService;
    private final ContractorToRoleService contractorToRoleService;
    private final DealDetailsService dealDetailsService;
    private final DealExportService dealExportService;

    @PutMapping("/save")
    public ResponseEntity<DealDto> saveDeal(@RequestBody DealDto dto) {
        DealDto saved = dealService.saveDeal(dto);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/change/status")
    public ResponseEntity<DealDto> changeStatus(@RequestBody DealStatusChangeRequest request) {
        DealDto result = dealService.changeDealStatus(request.getDealId(), request.getStatusId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealDetailsDto> getDealDetails(@PathVariable UUID id) {
        DealDetailsDto dto = dealDetailsService.getDealDetails(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<DealDetailsDto>> searchDeals(@RequestBody DealSearchRequest filter) {
        return ResponseEntity.ok(dealService.searchDeals(filter));
    }

    @PostMapping("/search/export")
    public ResponseEntity<byte[]> exportDealsToExcel(@RequestBody DealSearchRequest filter) {
        byte[] excel = dealExportService.exportDealsToExcel(filter);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=deals_page_export.xlsx")
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }

}
