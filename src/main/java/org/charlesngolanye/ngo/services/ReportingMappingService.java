package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportMappingRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportMappingRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportMappingResponseDto;
import org.charlesngolanye.ngo.entities.*;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportLineNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportTemplateNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportingMappingNotFoundException;
import org.charlesngolanye.ngo.mappers.ReportMappingMapper;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.charlesngolanye.ngo.repositories.ReportMappingRepository;
import org.charlesngolanye.ngo.repositories.ReportTemplateRepository;
import org.charlesngolanye.ngo.repositories.ReportingLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportingMappingService {
    private final ReportMappingRepository reportMappingRepository;
    private final ReportTemplateRepository reportTemplateRepository;
    private final ReportingLineRepository reportingLineRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final ReportMappingMapper reportMappingMapper;

    public ReportMappingResponseDto create(ReportMappingRequestDto requestDto) {
        ReportTemplate template = reportTemplateRepository.findById(requestDto.getReportTemplateId())
                .orElseThrow(() -> new ReportTemplateNotFoundException(
                        "Report template not found with ID: " + requestDto.getReportTemplateId()));

        template.verifyIsActive();

        ReportLine reportLine = reportingLineRepository.findById(requestDto.getReportLineId())
                .orElseThrow(() -> new ReportLineNotFoundException(
                        "Report line not found with ID: " + requestDto.getReportLineId()));

        BudgetCategory budgetCategory = budgetCategoryRepository.findById(requestDto.getBudgetCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException(
                        "Budget category not found with ID: " + requestDto.getBudgetCategoryId()));

        // 1. Domain validations
        validateReportLineBelongsToTemplate(reportLine, template);
        validateFrameworkConsistency(reportLine, template);

        // 2. Unique mapping check
        if (reportMappingRepository.existsByReportTemplateIdAndBudgetCategoryId(
                template.getId(), budgetCategory.getId())) {
            throw new IllegalArgumentException("Budget category is already mapped in this template");
        }

        // 3. Map and attach managed entities
        ReportingMapping mapping = reportMappingMapper.toEntity(requestDto);
        mapping.setReportTemplate(template);
        mapping.setReportLine(reportLine);
        mapping.setBudgetCategory(budgetCategory);

        return reportMappingMapper.toDto(reportMappingRepository.save(mapping));
    }

    @Transactional(readOnly = true)
    public ReportMappingResponseDto getById(Long id) {
        ReportingMapping reportingMapping = reportMappingRepository.findById(id)
                .orElseThrow(() -> new ReportingMappingNotFoundException("Reporting mapping not found"));
        return reportMappingMapper.toDto(reportingMapping);
    }

    @Transactional(readOnly = true)
    public List<ReportMappingResponseDto> getAll() {
        return reportMappingRepository.findAll()
                .stream()
                .map(reportMappingMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReportMappingResponseDto getByReportTemplateIdAndBudgetCategoryId
            (Long reportTemplateId, Long budgetCategoryId) {

        if (reportTemplateId == null || budgetCategoryId == null) {
            throw new IllegalArgumentException("Report template ID and Budget category ID must not be null");
        }

        ReportingMapping reportingMapping = reportMappingRepository
                .findByReportTemplateIdAndBudgetCategoryId(reportTemplateId, budgetCategoryId)
                .orElseThrow(() -> new ReportingMappingNotFoundException(
                        "Reporting mapping not found for template ID " + reportTemplateId + " and budget category ID " + budgetCategoryId));

        return reportMappingMapper.toDto(reportingMapping);
    }

    public ReportMappingResponseDto update(Long id, UpdateReportMappingRequest request) {
        ReportingMapping reportingMapping = reportMappingRepository.findById(id)
                .orElseThrow(() -> new ReportingMappingNotFoundException("Reporting mapping not found with ID: " + id));

        // Verify current template status before modifications
        reportingMapping.getReportTemplate().verifyIsActive();

        // 2. Resolve safe target IDs before applying structural mapper updates
        Long targetTemplateId = request.getReportTemplateId() != null
                ? request.getReportTemplateId()
                : reportingMapping.getReportTemplate().getId();

        Long targetLineId = request.getReportLineId() != null
                ? request.getReportLineId()
                : reportingMapping.getReportLine().getId();

        Long targetCategoryId = request.getBudgetCategoryId() != null
                ? request.getBudgetCategoryId()
                : reportingMapping.getBudgetCategory().getId();

        // 3. Fetch managed entities
        ReportTemplate template = reportTemplateRepository.findById(targetTemplateId)
                .orElseThrow(() -> new ReportTemplateNotFoundException("Report template not found with ID: " + targetTemplateId));

        ReportLine reportLine = reportingLineRepository.findById(targetLineId)
                .orElseThrow(() -> new ReportLineNotFoundException("Report line not found with ID: " + targetLineId));

        BudgetCategory budgetCategory = budgetCategoryRepository.findById(targetCategoryId)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Budget category not found with ID: " + targetCategoryId));

        // Ensure target template is active if template association changed
        template.verifyIsActive();

        // 4. Validate duplicate mappings excluding current record
        boolean existsOther = reportMappingRepository
                .existsByReportTemplateIdAndBudgetCategoryIdAndIdNot(
                        template.getId(), budgetCategory.getId(), id);

        if (existsOther) {
            throw new IllegalArgumentException("Budget category is already mapped to another entry in this template");
        }

        // 5. Cross-domain validations
        validateReportLineBelongsToTemplate(reportLine, template);
        validateFrameworkConsistency(reportLine, template);


        // 6. Map updates and assign managed entities
        reportMappingMapper.update(request, reportingMapping);
        reportingMapping.setReportTemplate(template);
        reportingMapping.setReportLine(reportLine);
        reportingMapping.setBudgetCategory(budgetCategory);

        return reportMappingMapper.toDto(reportMappingRepository.save(reportingMapping));
    }

    public void delete(Long id) {
        ReportingMapping reportingMapping = reportMappingRepository.findById(id)
                .orElseThrow(() -> new ReportingMappingNotFoundException("Reporting mapping not found"));

        reportingMapping.getReportTemplate().verifyIsActive();

        reportMappingRepository.delete(reportingMapping);
    }


    /**
     * Rule 1: Validate that the ReportLine's section belongs to the targeted ReportTemplate.
     */
    private void validateReportLineBelongsToTemplate(ReportLine reportLine, ReportTemplate template) {
        if (reportLine.getSection() == null) {
            throw new IllegalStateException("Report line " + reportLine.getId() + " is not assigned to a section.");
        }

        if (reportLine.getSection().getTemplate() == null) {
            throw new IllegalStateException("Section " + reportLine.getSection().getId() + " is not assigned to a template.");
        }

        Long actualTemplateId = reportLine.getSection().getTemplate().getId();
        if (!actualTemplateId.equals(template.getId())) {
            throw new IllegalArgumentException(
                    String.format("Report line (ID: %d) belongs to template ID %d, but template ID %d was provided.",
                            reportLine.getId(), actualTemplateId, template.getId())
            );
        }
    }

    /**
     * Rule 2: Prevent Framework Mismatch between ReportTemplate and ReportingCode.
     */
    private void validateFrameworkConsistency(ReportLine reportLine, ReportTemplate template) {
        ReportingCode reportingCode = reportLine.getReportingCode();

        if (reportingCode == null) {
            throw new IllegalStateException("Report line " + reportLine.getId() + " does not have an assigned reporting code.");
        }

        if (template.getFramework() != reportingCode.getFramework()) {
            throw new IllegalArgumentException(
                    String.format("Framework mismatch! Template '%s' uses framework '%s', but ReportingCode '%s' uses framework '%s'.",
                            template.getName(),
                            template.getFramework(),
                            reportingCode.getCode(),
                            reportingCode.getFramework())
            );
        }
    }
}
