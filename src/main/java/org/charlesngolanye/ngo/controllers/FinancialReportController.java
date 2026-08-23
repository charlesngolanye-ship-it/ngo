package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.responseDtos.FinancialSummaryResponseDto;
import org.charlesngolanye.ngo.services.FinancialReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports/financial-summary")
@Tag(name = "Reports")
public class FinancialReportController {
    private final FinancialReportService financialReportService;

    // GET /reports/financial-summary -> Overall NGO-wide report
    @GetMapping
    public ResponseEntity<FinancialSummaryResponseDto> getOverallSummary() {
        return ResponseEntity.ok(financialReportService.getOverallFinancialSummary());
    }

    // GET /reports/financial-summary/grants/1 -> Specific grant report
    @GetMapping("/grants/{grantId}")
    public ResponseEntity<FinancialSummaryResponseDto> getGrantSummary(@PathVariable Long grantId) {
        return ResponseEntity.ok(financialReportService.getGrantFinancialSummary(grantId));
    }
}
