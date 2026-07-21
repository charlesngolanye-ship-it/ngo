package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.responseDtos.FinancialSummaryResponseDto;
import org.charlesngolanye.ngo.services.FinancialReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
@Tag(name = "Reports")
public class FinancialReportController {
    private final FinancialReportService financialReportService;

    @GetMapping("/financial-summary")
    public ResponseEntity<FinancialSummaryResponseDto> getFinancialSummary() {
        return ResponseEntity.ok(financialReportService.getFinancialSummary());
    }
}
