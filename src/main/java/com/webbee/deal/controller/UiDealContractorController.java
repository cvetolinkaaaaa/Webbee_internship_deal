package com.webbee.deal.controller;

import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.service.DealContractorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * UI контроллер для управления связями между сделками и контрагентами (требует авторизации).
 */
@RestController
@RequestMapping("/ui/deal-contractor")
@RequiredArgsConstructor
@Tag(name = "UI Связь Сделка-Контрагент", description = "UI операции для управления связями между сделками и контрагентами (требуют авторизации)")
@SecurityRequirement(name = "bearerAuth")
public class UiDealContractorController {

    private final DealContractorService dealContractorService;

    /**
     * Сохраняет или обновляет связь между сделкой и контрагентом.
     */
    @Operation(
            summary = "Сохранить или обновить связь сделки и контрагента",
            description = "Создает новую или обновляет существующую связь между сделкой и контрагентом (требует авторизации)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Связь успешно создана или обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")})
    @PutMapping("/save")
    @PreAuthorize("hasRole('DEAL_SUPERUSER') or hasRole('SUPERUSER')")
    public ResponseEntity saveDealContractor(@RequestBody DealContractorDto dto) {

        dealContractorService.saveDealContractor(dto);
        return ResponseEntity.ok(HttpStatus.CREATED);

    }

    /**
     * Удаляет связь между сделкой и контрагентом по идентификатору.
     */
    @Operation(
            summary = "Удалить связь сделки и контрагента",
            description = "Удаляет связь между сделкой и контрагентом по идентификатору (требует авторизации)"
    )
    @ApiResponse(responseCode = "204", description = "Связь успешно удалена")
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('DEAL_SUPERUSER') or hasRole('SUPERUSER')")
    public ResponseEntity deleteDealContractor(
            @RequestParam("id") UUID dealContractorId
    ) {

        dealContractorService.deleteDealContractor(dealContractorId);
        return ResponseEntity.noContent().build();

    }

}
