package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportSectionRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportSectionRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportSectionResponseDto;
import org.charlesngolanye.ngo.entities.ReportSection;
import org.charlesngolanye.ngo.entities.ReportTemplate;
import org.charlesngolanye.ngo.exceptions.ReportSectionNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportTemplateNotFoundException;
import org.charlesngolanye.ngo.mappers.ReportSectionMapper;
import org.charlesngolanye.ngo.repositories.ReportSectionRepository;
import org.charlesngolanye.ngo.repositories.ReportTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportSectionService {
    private final ReportSectionRepository reportSectionRepository;
    private final ReportTemplateRepository reportTemplateRepository;
    private final ReportSectionMapper reportSectionMapper;

    public ReportSectionResponseDto create(ReportSectionRequestDto requestDto) {
        ReportTemplate template = reportTemplateRepository.findById(requestDto.getTemplateId())
                .orElseThrow(() -> new ReportTemplateNotFoundException(
                        "Report template not found with ID: " + requestDto.getTemplateId()));

        // Verify active state
        template.verifyIsActive();

        ReportSection reportSection = reportSectionMapper.toEntity(requestDto);
        reportSection.setTemplate(template);

        ReportSection savedReportSection = reportSectionRepository.save(reportSection);
        return reportSectionMapper.toDto(savedReportSection);
    }

    @Transactional(readOnly = true)
    public ReportSectionResponseDto getById(Long id) {
        ReportSection reportSection = reportSectionRepository.findById(id)
                .orElseThrow(() -> new ReportSectionNotFoundException("Report section not found with ID: " + id));
        return reportSectionMapper.toDto(reportSection);
    }

    @Transactional(readOnly = true)
    public List<ReportSectionResponseDto> getAll() {
        return reportSectionMapper.toDtoList(reportSectionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ReportSectionResponseDto> getByTemplateId(Long templateId) {
        if (templateId == null) {
            throw new IllegalArgumentException("Template ID must not be null");
        }
        List<ReportSection> reportSections = reportSectionRepository.findByTemplateIdOrderByDisplayOrderAsc(templateId);
        return reportSectionMapper.toDtoList(reportSections);
    }

    public ReportSectionResponseDto update(Long id, UpdateReportSectionRequest request) {
        ReportSection reportSection = reportSectionRepository.findById(id)
                .orElseThrow(() -> new ReportSectionNotFoundException("Report section not found with ID: " + id));

        // Guard check on current parent template
        reportSection.getTemplate().verifyIsActive();

        reportSectionMapper.update(request, reportSection);

        //Fetch fresh managed entity if template ID changed and verify target status
        ReportTemplate currentTemplate = reportTemplateRepository.findById(reportSection.getTemplate().getId())
                .orElseThrow(() -> new ReportTemplateNotFoundException(
                        "Report template not found with ID: " + reportSection.getTemplate().getId()));

        currentTemplate.verifyIsActive();

        reportSection.setTemplate(currentTemplate);
        return reportSectionMapper.toDto(reportSectionRepository.save(reportSection));
    }

    public void delete(Long id) {
        ReportSection reportSection = reportSectionRepository.findById(id)
                .orElseThrow(() -> new ReportSectionNotFoundException("Report section not found with ID: " + id));

        reportSection.getTemplate().verifyIsActive();

        reportSectionRepository.delete(reportSection);
    }
}
