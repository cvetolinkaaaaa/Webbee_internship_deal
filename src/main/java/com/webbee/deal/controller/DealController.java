package com.webbee.deal.controller;

import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.dto.DealDto;
import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.dto.DealStatusChangeRequest;
import com.webbee.deal.service.DealDetailsService;
import com.webbee.deal.service.DealExportService;
import com.webbee.deal.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * Контроллер для управления сделками.
 */
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Tag(name = "Сделки", description = "Операции для работы со сделками")
public class DealController {

    private final DealService dealService;
    private final DealDetailsService dealDetailsService;
    private final DealExportService dealExportService;

    /**
     * Создает новую сделку или изменяет существующую.
     */
    @Operation(
            summary = "Создать или изменить сделку",
            description = "Создает новую сделку или изменяет существующую"
    )
    @ApiResponse(responseCode = "201", description = "Сделка успешно создана")
    @PutMapping("/save")
    public ResponseEntity<DealDto> saveDeal(@RequestBody DealDto dto) {
        DealDto saved = dealService.saveDeal(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Изменяет статус сделки.
     */
    @Operation(
            summary = "Изменить статус сделки",
            description = "Изменяет статус сделки по её id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус сделки успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Сделка не найдена")
    })
    @PatchMapping("/change/status")
    public ResponseEntity<DealDto> changeStatus(@RequestBody DealStatusChangeRequest request) {
        DealDto result = dealService.changeDealStatus(request.getDealId(), request.getStatusId());
        return ResponseEntity.ok(result);
    }

    /**
     * Получает сделку по её id.
     */
    @Operation(
            summary = "Получить сделку по ID",
            description = "Возвращает сделку по заданному ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сделка найдена"),
            @ApiResponse(responseCode = "404", description = "Сделка не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DealDetailsDto> getDealDetails(@PathVariable UUID id) {
        DealDetailsDto dto = dealDetailsService.getDealDetails(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Осуществляет поиск сделок по фильтру.
     */
    @Operation(
            summary = "Поиск сделок",
            description = "Позволяет выполнять поиск сделок с использованием фильтров и пагинации"
    )
    @ApiResponse(responseCode = "200", description = "Результаты поиска успешно получены")
    @PostMapping("/search")
    public ResponseEntity<Page<DealDetailsDto>> searchDeals(@RequestBody DealSearchRequest filter) {
        return ResponseEntity.ok(dealService.searchDeals(filter));
    }

    /**
     * Экспортирует найденные сделки в Excel-файл.
     */
    @Operation(
            summary = "Экспортировать сделки в Excel",
            description = "Выполняет поиск сделок по фильтру и экспортирует результат в Excel-файл"
    )
    @ApiResponse(responseCode = "200", description = "Экспорт выполнен успешно")
    @PostMapping("/search/export")
    public ResponseEntity<byte[]> exportDealsToExcel(@RequestBody DealSearchRequest filter) {
        byte[] excel = dealExportService.exportDealsToExcel(filter);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=deals_page_export.xlsx")
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }

}
