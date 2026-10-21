package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.responseDtos.GeneratedReportLineDto;
import org.charlesngolanye.ngo.dtos.responseDtos.GeneratedReportResponseDto;
import org.charlesngolanye.ngo.dtos.responseDtos.GeneratedReportSectionDto;
import org.charlesngolanye.ngo.entities.*;
import org.charlesngolanye.ngo.exceptions.ReportTemplateNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportingPeriodNotFoundException;
import org.charlesngolanye.ngo.reporting.ReportCalculationEngine;
import org.charlesngolanye.ngo.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportGenerationService {

    private final ReportTemplateRepository reportTemplateRepository;
    private final ReportingPeriodRepository reportingPeriodRepository;
    private final ReportSectionRepository reportSectionRepository;
    private final ReportLineRepository reportLineRepository;
    private final ReportingMappingRepository reportingMappingRepository;
    private final ExpenseRepository expenseRepository;

    private final ReportCalculationEngine calculationEngine;

    public GeneratedReportResponseDto generate(Long templateId, Long reportingPeriodId) {

        ReportTemplate template = reportTemplateRepository.findById(templateId)
                        .orElseThrow(() -> new ReportTemplateNotFoundException(
                                        "Report template not found with ID: " + templateId));

        template.verifyIsActive();

        ReportingPeriod period = reportingPeriodRepository.findById(reportingPeriodId)
                        .orElseThrow(() -> new ReportingPeriodNotFoundException(
                                        "Reporting period not found with ID: " + reportingPeriodId));

        Grant grant = period.getGrant();

        List<ReportingMapping> mappings = reportingMappingRepository.findByReportTemplateId(templateId);

        List<Expense> expenses = expenseRepository.findByGrantIdAndExpenseDateBetween(
                                grant.getId(),
                                period.getStartDate(),
                                period.getEndDate()
                        );

        List<ReportSection> sections = reportSectionRepository.findByTemplateIdOrderByDisplayOrderAsc(templateId);

        List<GeneratedReportSectionDto> sectionDtos = new ArrayList<>();

        for (ReportSection section : sections) {
            List<ReportLine> lines = reportLineRepository.findBySectionIdOrderByDisplayOrderAsc(section.getId());

            List<GeneratedReportLineDto> lineDtos = new ArrayList<>();
            for (ReportLine line : lines) {
                BigDecimal value = calculateLine(line, mappings, expenses);

                String reportingCodeValue = (line.getReportingCode() != null)
                        ? line.getReportingCode().getCode()
                        : null;

                GeneratedReportLineDto lineDto = new GeneratedReportLineDto(
                        line.getId(),
                        reportingCodeValue,
                        line.getName(),
                        line.getCalculationType(),
                        line.getDisplayOrder(),
                        value
                );
                lineDtos.add(lineDto);
            }

            GeneratedReportSectionDto sectionDto = new GeneratedReportSectionDto(
                    section.getId(),
                    section.getCode(),
                    section.getName(),
                    section.getDisplayOrder(),
                    lineDtos
            );
            sectionDtos.add(sectionDto);
        }

        return new GeneratedReportResponseDto(
                template.getId(),
                template.getName(),
                template.getFramework(),
                template.getVersion(),
                grant.getId(),
                grant.getGrantNumber(),
                grant.getGrantName(),
                period.getId(),
                period.getStartDate(),
                period.getEndDate(),
                sectionDtos
        );

    }

    private BigDecimal calculateLine(
            ReportLine line,
            List<ReportingMapping> mappings,
            List<Expense> expenses) {

        if (line.getCalculationType() == null) {
            return BigDecimal.ZERO;
        }

        return switch (line.getCalculationType()) {
            case SUM -> calculationEngine.calculateSum(line, mappings, expenses);
            default -> throw new UnsupportedOperationException("Calculation type " + line.getCalculationType() + " is not yet supported");
        };
    }
}
