package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingMappingRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingMappingRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingMappingResponseDto;
import org.charlesngolanye.ngo.entities.*;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportLineNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportTemplateNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportingMappingNotFoundException;
import org.charlesngolanye.ngo.mappers.ReportingMappingMapper;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.charlesngolanye.ngo.repositories.ReportingMappingRepository;
import org.charlesngolanye.ngo.repositories.ReportTemplateRepository;
import org.charlesngolanye.ngo.repositories.ReportLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportingMappingService {
    private final ReportingMappingRepository reportingMappingRepository;
    private final ReportTemplateRepository reportTemplateRepository;
    private final ReportLineRepository reportLineRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final ReportingMappingMapper reportingMappingMapper;

    public ReportingMappingResponseDto create(ReportingMappingRequestDto requestDto) {
        ReportTemplate template = reportTemplateRepository.findById(requestDto.getReportTemplateId())
                .orElseThrow(() -> new ReportTemplateNotFoundException(
                        "Report template not found with ID: " + requestDto.getReportTemplateId()));

        template.verifyIsActive();

        ReportLine reportLine = reportLineRepository.findById(requestDto.getReportLineId())
                .orElseThrow(() -> new ReportLineNotFoundException(
                        "Report line not found with ID: " + requestDto.getReportLineId()));

        BudgetCategory budgetCategory = budgetCategoryRepository.findById(requestDto.getBudgetCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException(
                        "Budget category not found with ID: " + requestDto.getBudgetCategoryId()));

        // 1. Domain validations
        validateReportLineBelongsToTemplate(reportLine, template);
        validateFrameworkConsistency(reportLine, template);

        // 2. Unique mapping check
        if (reportingMappingRepository.existsByReportTemplateIdAndBudgetCategoryId(
                template.getId(), budgetCategory.getId())) {
            throw new IllegalArgumentException("Budget category is already mapped in this template");
        }

        // 3. Map and attach managed entities
        ReportingMapping mapping = reportingMappingMapper.toEntity(requestDto);
        mapping.setReportTemplate(template);
        mapping.setReportLine(reportLine);
        mapping.setBudgetCategory(budgetCategory);

        return reportingMappingMapper.toDto(reportingMappingRepository.save(mapping));
    }

    @Transactional(readOnly = true)
    public ReportingMappingResponseDto getById(Long id) {
        ReportingMapping reportingMapping = reportingMappingRepository.findById(id)
                .orElseThrow(() -> new ReportingMappingNotFoundException("Reporting mapping not found"));
        return reportingMappingMapper.toDto(reportingMapping);
    }

    @Transactional(readOnly = true)
    public List<ReportingMappingResponseDto> getAll() {
        return reportingMappingRepository.findAll()
                .stream()
                .map(reportingMappingMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReportingMappingResponseDto getByReportTemplateIdAndBudgetCategoryId
            (Long reportTemplateId, Long budgetCategoryId) {

        if (reportTemplateId == null || budgetCategoryId == null) {
            throw new IllegalArgumentException("Report template ID and Budget category ID must not be null");
        }

        ReportingMapping reportingMapping = reportingMappingRepository
                .findByReportTemplateIdAndBudgetCategoryId(reportTemplateId, budgetCategoryId)
                .orElseThrow(() -> new ReportingMappingNotFoundException(
                        "Reporting mapping not found for template ID " + reportTemplateId + " and budget category ID " + budgetCategoryId));

        return reportingMappingMapper.toDto(reportingMapping);
    }

    public ReportingMappingResponseDto update(Long id, UpdateReportingMappingRequest request) {
        ReportingMapping reportingMapping = reportingMappingRepository.findById(id)
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

        ReportLine reportLine = reportLineRepository.findById(targetLineId)
                .orElseThrow(() -> new ReportLineNotFoundException("Report line not found with ID: " + targetLineId));

        BudgetCategory budgetCategory = budgetCategoryRepository.findById(targetCategoryId)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Budget category not found with ID: " + targetCategoryId));

        // Ensure target template is active if template association changed
        template.verifyIsActive();

        // 4. Validate duplicate mappings excluding current record
        boolean existsOther = reportingMappingRepository
                .existsByReportTemplateIdAndBudgetCategoryIdAndIdNot(
                        template.getId(), budgetCategory.getId(), id);

        if (existsOther) {
            throw new IllegalArgumentException("Budget category is already mapped to another entry in this template");
        }

        // 5. Cross-domain validations
        validateReportLineBelongsToTemplate(reportLine, template);
        validateFrameworkConsistency(reportLine, template);


        // 6. Map updates and assign managed entities
        reportingMappingMapper.update(request, reportingMapping);
        reportingMapping.setReportTemplate(template);
        reportingMapping.setReportLine(reportLine);
        reportingMapping.setBudgetCategory(budgetCategory);

        return reportingMappingMapper.toDto(reportingMappingRepository.save(reportingMapping));
    }

    public void delete(Long id) {
        ReportingMapping reportingMapping = reportingMappingRepository.findById(id)
                .orElseThrow(() -> new ReportingMappingNotFoundException("Reporting mapping not found"));

        reportingMapping.getReportTemplate().verifyIsActive();

        reportingMappingRepository.delete(reportingMapping);
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
