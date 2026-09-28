package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportTemplateRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportTemplateRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportTemplateResponseDto;
import org.charlesngolanye.ngo.entities.Framework;
import org.charlesngolanye.ngo.entities.ReportTemplate;
import org.charlesngolanye.ngo.entities.TemplateStatus;
import org.charlesngolanye.ngo.exceptions.ReportTemplateNotFoundException;
import org.charlesngolanye.ngo.mappers.ReportTemplateMapper;
import org.charlesngolanye.ngo.repositories.ReportTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportTemplateService {
    private final ReportTemplateRepository reportTemplateRepository;
    private final ReportTemplateMapper reportTemplateMapper;

    public ReportTemplateResponseDto create(ReportTemplateRequestDto requestDto) {
        ReportTemplate reportTemplate = reportTemplateMapper.toEntity(requestDto);
        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        return reportTemplateMapper.toDto(savedReportTemplate);
    }

    @Transactional(readOnly = true)
    public ReportTemplateResponseDto getById(Long id) {
        ReportTemplate reportTemplate = reportTemplateRepository.findById(id)
                .orElseThrow(() -> new ReportTemplateNotFoundException("Report template not found with ID: " + id));
        return reportTemplateMapper.toDto(reportTemplate);
    }

    @Transactional(readOnly = true)
    public List<ReportTemplateResponseDto> getAll() {
        return reportTemplateMapper.toDtoList(reportTemplateRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ReportTemplateResponseDto> getByFramework(Framework framework) {
        if (framework == null) {
            throw new IllegalArgumentException("Framework must not be null");
        }
        return reportTemplateMapper.toDtoList(reportTemplateRepository.findByFramework(framework));
    }

    @Transactional(readOnly = true)
    public List<ReportTemplateResponseDto> getByTemplateStatus(TemplateStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Template status must not be null");
        }
        return reportTemplateMapper.toDtoList(reportTemplateRepository.findByTemplateStatus(status));
    }

    @Transactional(readOnly = true)
    public List<ReportTemplateResponseDto> getByFrameworkAndStatus(Framework framework, TemplateStatus status) {
        if (framework == null || status == null) {
            throw new IllegalArgumentException("Framework and Template status must not be null");
        }
        return reportTemplateMapper.toDtoList(reportTemplateRepository.findByFrameworkAndTemplateStatus(framework, status));
    }

    public ReportTemplateResponseDto update(Long id, UpdateReportTemplateRequest request) {
        ReportTemplate reportTemplate = reportTemplateRepository.findById(id)
                .orElseThrow(() -> new ReportTemplateNotFoundException("Report template not found with ID: " + id));

        reportTemplateMapper.update(request, reportTemplate);
        return reportTemplateMapper.toDto(reportTemplateRepository.save(reportTemplate));
    }

    public void delete(Long id) {
        ReportTemplate reportTemplate = reportTemplateRepository.findById(id)
                .orElseThrow(() -> new ReportTemplateNotFoundException("Report template not found with ID: " + id));

        reportTemplateRepository.delete(reportTemplate);
    }
}
