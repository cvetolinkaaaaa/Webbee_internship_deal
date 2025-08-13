package com.webbee.deal.service;

import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.dto.DealStatusShortDto;
import com.webbee.deal.dto.DealSumShortDto;
import com.webbee.deal.dto.DealTypeShortDto;
import com.webbee.deal.security.service.AuthorizationService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class DealExportServiceTest {

    @Mock
    private DealService dealService;
    
    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private DealExportService service;

    private DealSearchRequest searchRequest;
    private DealDetailsDto testDeal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        searchRequest = new DealSearchRequest();
        testDeal = createTestDealDetailsDto();
    }

    @Test
    @DisplayName("Успешно экспортирует сделки в Excel")
    void exportDealsToExcel_WithValidData_ReturnsExcelBytes() throws IOException {

        Page<DealDetailsDto> page = new PageImpl<>(List.of(testDeal));
        when(dealService.searchDeals(searchRequest)).thenReturn(page);
        byte[] result = service.exportDealsToExcel(searchRequest);
        assertThat(result).isNotNull();
        assertThat(result.length).isGreaterThan(0);
        verifyExcelContent(result);

    }

    @Test
    @DisplayName("Успешно экспортирует пустой список сделок")
    void exportDealsToExcel_WithEmptyList_ReturnsExcelWithHeaders() throws IOException {

        Page<DealDetailsDto> emptyPage = new PageImpl<>(Collections.emptyList());
        when(dealService.searchDeals(searchRequest)).thenReturn(emptyPage);
        byte[] result = service.exportDealsToExcel(searchRequest);
        assertThat(result).isNotNull();
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
            Sheet sheet = workbook.getSheet("Deals");
            assertThat(sheet.getLastRowNum()).isEqualTo(0);
        }

    }

    @Test
    @DisplayName("Успешно экспортирует сделки с учетом прав доступа")
    void exportDealsToExcelWithAuth_WithValidData_AppliesAuthFilter() {

        DealSearchRequest filteredRequest = new DealSearchRequest();
        Page<DealDetailsDto> page = new PageImpl<>(List.of(testDeal));
        when(authorizationService.applyDealAccessFilter(searchRequest)).thenReturn(filteredRequest);
        when(dealService.searchDeals(filteredRequest)).thenReturn(page);
        byte[] result = service.exportDealsToExcelWithAuth(searchRequest);
        assertThat(result).isNotNull();
        assertThat(result.length).isGreaterThan(0);

    }

    @Test
    @DisplayName("Выбрасывает исключение при ошибке создания Excel")
    void exportDealsToExcel_WithServiceException_ThrowsRuntimeException() {

        when(dealService.searchDeals(any())).thenThrow(new RuntimeException("Database error"));
        assertThatThrownBy(() -> service.exportDealsToExcel(searchRequest))
                .isInstanceOf(RuntimeException.class);

    }

    private DealDetailsDto createTestDealDetailsDto() {

        DealDetailsDto deal = new DealDetailsDto();
        deal.setId(UUID.randomUUID());
        deal.setDescription("Test Deal");
        deal.setAgreementNumber("AGR-001");
        deal.setAgreementDate(LocalDate.now());
        deal.setAgreementStartDt(LocalDateTime.now());
        deal.setAvailabilityDate(LocalDate.now());
        deal.setCloseDt(LocalDateTime.now());
        deal.setType(new DealTypeShortDto("CREDIT", "Кредит"));
        deal.setStatus(new DealStatusShortDto("ACTIVE", "Активный"));
        deal.setSum(new DealSumShortDto("100000", "RUB"));
        return deal;

    }

    private void verifyExcelContent(byte[] excelBytes) throws IOException {

        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes))) {
            Sheet sheet = workbook.getSheet("Deals");
            assertThat(sheet).isNotNull();
            Row headerRow = sheet.getRow(0);
            assertThat(headerRow.getCell(0).getStringCellValue()).isEqualTo("ID сделки");
            assertThat(headerRow.getCell(1).getStringCellValue()).isEqualTo("Наименование сделки");
            if (sheet.getLastRowNum() > 0) {
                Row dataRow = sheet.getRow(1);
                assertThat(dataRow.getCell(0).getStringCellValue()).isEqualTo(testDeal.getId().toString());
                assertThat(dataRow.getCell(1).getStringCellValue()).isEqualTo("Test Deal");
            }
        }

    }

}