package com.webbee.deal.controller;

import com.webbee.deal.dto.DealDto;
import com.webbee.deal.dto.DealStatusChangeRequest;
import com.webbee.deal.service.ContractorToRoleService;
import com.webbee.deal.service.DealContractorService;
import com.webbee.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/deal")
public class DealController {

    private final DealService dealService;
    private final DealContractorService contractorService;
    private final ContractorToRoleService contractorToRoleService;

    public DealController(DealService dealService, DealContractorService contractorService, ContractorToRoleService contractorToRoleService) {
        this.dealService = dealService;
        this.contractorService = contractorService;
        this.contractorToRoleService = contractorToRoleService;
    }

    // Можно добавить и другие сервисы по необходимости

    // Создать или обновить сделку (PUT /deal/save)
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

//
//    // PATCH /deal/change/status
//    @PatchMapping("/change/status")
//    public ResponseEntity<DealDto> changeStatus(@RequestParam UUID dealId,
//                                                @RequestParam String statusId) {
//        DealDto updated = dealService.updateDealStatus(dealId, statusId);
//        return ResponseEntity.ok(updated);
//    }
//
//    // Получить сделку по id (GET /deal/{id})
//    @GetMapping("/{id}")
//    public ResponseEntity<DealDto> getDeal(@PathVariable UUID id) {
//        return dealService.getDeal(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Поиск сделок с пагинацией (POST /deal/search)
//    @PostMapping("/search")
//    public ResponseEntity<Page<DealDto>> searchDeals(@RequestBody DealSearchDto filter, Pageable pageable) {
//        return ResponseEntity.ok(dealService.search(filter, pageable));
//    }
//
//    // Экспорт в Excel (POST /deal/search/export)
//    @PostMapping("/search/export")
//    public ResponseEntity<byte[]> exportDeals(@RequestBody DealSearchDto filter, Pageable pageable) {
//        byte[] excel = dealService.exportToExcel(filter, pageable);
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=deals_export.xlsx")
//                .body(excel);
//    }
//
//    // Добавить или обновить контрагента сделки (PUT /deal-contractor/save)
//    @PutMapping("/deal-contractor/save")
//    public ResponseEntity<DealContractorDto> saveDealContractor(@RequestBody DealContractorDto dto) {
//        DealContractorDto saved = contractorService.saveDealContractor(dto);
//        return ResponseEntity.ok(saved);
//    }
//
//    // Логическое удаление контрагента сделки (DELETE /deal-contractor/delete)
//    @DeleteMapping("/deal-contractor/delete")
//    public ResponseEntity<Void> deleteDealContractor(@RequestParam UUID contractorId) {
//        contractorService.logicalDelete(contractorId);
//        return ResponseEntity.ok().build();
//    }
//
//    // Добавить роль контрагенту сделки (POST /contractor-to-role/add)
//    @PostMapping("/contractor-to-role/add")
//    public ResponseEntity<ContractorToRoleDto> addRole(@RequestBody ContractorToRoleDto dto) {
//        ContractorToRoleDto saved = contractorToRoleService.addRoleToContractor(dto);
//        return ResponseEntity.ok(saved);
//    }
//
//    // Логическое удаление роли у контрагента сделки (DELETE /contractor-to-role/delete)
//    @DeleteMapping("/contractor-to-role/delete")
//    public ResponseEntity<Void> deleteRole(@RequestParam UUID contractorId, @RequestParam String roleId) {
//        contractorToRoleService.deleteRoleFromContractor(contractorId, roleId);
//        return ResponseEntity.ok().build();
//    }
}
