package com.webbee.deal.service;

import com.webbee.deal.dto.DealContractorShortDto;
import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.dto.DealSearchRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * Сервис для экспорта данных о сделках в Excel.
 */
@Service
@RequiredArgsConstructor
public class DealExportService {

    private final DealService dealService;

    /**
     * Экспортирует сделки, удовлетворяющие фильтру, в Excel-файл формата .xlsx.
     */
    public byte[] exportDealsToExcel(DealSearchRequest filter) {
        Page<DealDetailsDto> page = dealService.searchDeals(filter);
        List<DealDetailsDto> deals = page.getContent();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Deals");
            String[] headers = {
                "ID сделки", "Наименование сделки", "Номер договора", "Дата договора", "Дата начала договора",
                "Дата доступности", "Тип сделки", "Статус сделки", "Сумма", "Валюта", "Дата закрытия",
                "Заемщики", "Поручители"
            };
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }
            int rowIdx = 1;
            for (DealDetailsDto deal : deals) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(deal.getId() != null ? deal.getId().toString() : "");
                row.createCell(1).setCellValue(deal.getDescription() != null ? deal.getDescription() : "");
                row.createCell(2).setCellValue(deal.getAgreementNumber() != null ? deal.getAgreementNumber() : "");
                row.createCell(3).setCellValue(deal.getAgreementDate() != null ? deal.getAgreementDate().toString() : "");
                row.createCell(4).setCellValue(deal.getAgreementStartDt() != null ? deal.getAgreementStartDt().toString() : "");
                row.createCell(5).setCellValue(deal.getAvailabilityDate() != null ? deal.getAvailabilityDate().toString() : "");
                row.createCell(6).setCellValue(deal.getType() != null ? deal.getType().getName() : "");
                row.createCell(7).setCellValue(deal.getStatus() != null ? deal.getStatus().getName() : "");
                if (deal.getSum() != null) {
                    row.createCell(8).setCellValue(deal.getSum().getValue() != null ? deal.getSum().getValue() : "");
                    row.createCell(9).setCellValue(deal.getSum().getCurrency() != null ? deal.getSum().getCurrency() : "");
                } else {
                    row.createCell(8).setCellValue("");
                    row.createCell(9).setCellValue("");
                }

                row.createCell(10).setCellValue(deal.getCloseDt() != null ? deal.getCloseDt().toString() : "");
                String borrowers = deal.getContractors() == null ? "" :
                    deal.getContractors().stream()
                        .filter(c -> c.getRoles() != null && c.getRoles().stream()
                            .anyMatch(r -> "BORROWER".equalsIgnoreCase(r.getCategory())))
                        .map(DealContractorShortDto::getName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                row.createCell(11).setCellValue(borrowers);
                String warrantors = deal.getContractors() == null ? "" :
                    deal.getContractors().stream()
                        .filter(c -> c.getRoles() != null && c.getRoles().stream()
                            .anyMatch(r -> "WARRANITY".equalsIgnoreCase(r.getCategory())))
                        .map(DealContractorShortDto::getName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                row.createCell(12).setCellValue(warrantors);
            }
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка экспорта Excel", e);
        }
    }

}
