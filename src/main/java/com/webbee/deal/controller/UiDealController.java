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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * UI контроллер для управления сделками (требует авторизации).
 */
@RestController
@RequestMapping("/ui/deal")
@RequiredArgsConstructor
@Tag(name = "UI Сделки", description = "UI операции для работы со сделками (требуют авторизации)")
@SecurityRequirement(name = "bearerAuth")
public class UiDealController {

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сделка успешно сохранена"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @PutMapping("/save")
    @PreAuthorize("@authorizationService.canModifyDeals() and !@authorizationService.isDealContractorAccessDenied()")
    public ResponseEntity saveDeal(@RequestBody DealDto dto) {

        dealService.saveDealWithAuth(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

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
            @ApiResponse(responseCode = "404", description = "Сделка или статус не найдены"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа"),
    })
    @PatchMapping("/change/status")
    @PreAuthorize("@authorizationService.canModifyDeals() and !@authorizationService.isDealContractorAccessDenied()")
    public ResponseEntity changeStatus(@RequestBody DealStatusChangeRequest request) {

        dealService.changeDealStatus(request.getDealId(), request.getStatusId());
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    /**
     * Осуществляет поиск сделок по фильтру с учетом прав доступа пользователя.
     * Автоматически фильтрует результаты по типам сделок, к которым у пользователя есть доступ.
     */
    @Operation(
            summary = "Поиск сделок",
            description = "Позволяет выполнять поиск сделок с использованием фильтров и пагинации (с учетом прав доступа пользователя)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результаты поиска успешно получены"),
            @ApiResponse(responseCode = "403", description = "Доступ к сделкам запрещен")
    })
    @PostMapping("/search")
    @PreAuthorize("!@authorizationService.isDealContractorAccessDenied()")
    public ResponseEntity<Page<DealDetailsDto>> searchDeals(@RequestBody DealSearchRequest filter) {
        Page<DealDetailsDto> results = dealService.searchDealsWithAuth(filter);
        return ResponseEntity.ok(results);
    }

    /**
     * Экспортирует найденные сделки в Excel-файл с учетом прав доступа пользователя.
     */
    @Operation(
            summary = "Экспортировать сделки в Excel",
            description = "Выполняет поиск сделок по фильтру и экспортирует результат в Excel-файл (с учетом прав доступа пользователя)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Экспорт выполнен успешно"),
            @ApiResponse(responseCode = "403", description = "Доступ к сделкам/контрагентам запрещен для роли ADMIN"),
            @ApiResponse(responseCode = "500", description = "Ошибка при создании Excel-файла")
    })
    @PostMapping("/search/export")
    @PreAuthorize("!@authorizationService.isDealContractorAccessDenied()")
    public ResponseEntity<byte[]> exportDealsToExcel(@RequestBody DealSearchRequest filter) {

        byte[] excel = dealExportService.exportDealsToExcelWithAuth(filter);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=deals_export.xlsx")
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);

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
            @ApiResponse(responseCode = "404", description = "Сделка не найдена"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа")
    })
    @GetMapping("/{id}")
    @PreAuthorize("@authorizationService.canViewReferenceData()")
    public ResponseEntity<DealDetailsDto> getDealDetails(@PathVariable UUID id) {
        DealDetailsDto dto = dealDetailsService.getDealDetails(id);
        return ResponseEntity.ok(dto);
    }

}
