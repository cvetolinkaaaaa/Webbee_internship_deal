package com.webbee.deal.controller;

import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.service.DealContractorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Контроллер для управления связями между сделками и контрагентами.
 */
@RestController
@RequestMapping("/deal-contractor")
@Tag(name = "Связь Сделка-Контрагент", description = "Операции для управления связями между сделками и контрагентами")
public class DealContractorController {

    private final DealContractorService dealContractorService;

    public DealContractorController(DealContractorService dealContractorService) {
        this.dealContractorService = dealContractorService;
    }

    /**
     * Сохраняет или обновляет связь между сделкой и контрагентом.
     */
    @Operation(
            summary = "Сохранить или обновить связь сделки и контрагента",
            description = "Создает новую или обновляет существующую связь между сделкой и контрагентом"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Связь успешно создана или обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")})
    @PutMapping("/save")
    public ResponseEntity saveDealContractor(@RequestBody DealContractorDto dto) {
        dealContractorService.saveDealContractor(dto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * Удаляет связь между сделкой и контрагентом по идентификатору.
     */
    @Operation(
            summary = "Удалить связь сделки и контрагента",
            description = "Удаляет связь между сделкой и контрагентом по идентификатору"
    )
    @ApiResponse(responseCode = "204", description = "Связь успешно удалена")
    @DeleteMapping("/delete")
    public ResponseEntity deleteDealContractor(
            @RequestParam("id") UUID dealContractorId
    ) {
        dealContractorService.deleteDealContractor(dealContractorId);
        return ResponseEntity.noContent().build();
    }

}
