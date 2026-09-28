package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingCodeRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingCodeRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingCodeResponseDto;
import org.charlesngolanye.ngo.entities.Framework;
import org.charlesngolanye.ngo.entities.ReportingCode;
import org.charlesngolanye.ngo.exceptions.ReportingCodeNotFoundException;
import org.charlesngolanye.ngo.mappers.ReportingCodeMapper;
import org.charlesngolanye.ngo.repositories.ReportingCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportingCodeService {
    private final ReportingCodeRepository reportingCodeRepository;
    private final ReportingCodeMapper reportingCodeMapper;

    public ReportingCodeResponseDto create(ReportingCodeRequestDto reportingCodeRequestDto) {
        ReportingCode reportingCode = reportingCodeMapper.toEntity(reportingCodeRequestDto);

        if (reportingCode.getFramework() == null) {
            throw new IllegalArgumentException("Framework is required");
        }

        if (reportingCode.getCode() == null ||
                reportingCode.getCode().isBlank()) {
            throw new IllegalArgumentException("Reporting code is required");
        }

        if (reportingCodeRepository.existsByFrameworkAndCode(
                reportingCode.getFramework(),
                reportingCode.getCode())) {

            throw new IllegalArgumentException(
                    "Reporting code already exists for this framework"
            );
        }

        ReportingCode savedReportingCode = reportingCodeRepository.save(reportingCode);

        return reportingCodeMapper.toDto(savedReportingCode);
    }

    public ReportingCodeResponseDto getById(Long id) {
        ReportingCode reportingCode = reportingCodeRepository.findById(id)
                .orElseThrow(() -> new ReportingCodeNotFoundException("Reporting code not found"));
        return reportingCodeMapper.toDto(reportingCode);
    }

    public List<ReportingCodeResponseDto> getAll() {
        return reportingCodeRepository.findAll()
                .stream()
                .map(reportingCodeMapper::toDto)
                .toList();
    }

    public List<ReportingCodeResponseDto> getByFramework(Framework framework) {
        if (framework == null) {
            throw new IllegalArgumentException("Framework must not be null");
        }
        List<ReportingCode> reportingCodes = reportingCodeRepository.findByFramework(framework);

        return reportingCodeMapper.toDtoList(reportingCodes);
    }

    public ReportingCodeResponseDto update(Long id, UpdateReportingCodeRequest request) {
        ReportingCode reportingCode = reportingCodeRepository.findById(id)
                .orElseThrow(() -> new ReportingCodeNotFoundException("Reporting code not found"));
        reportingCodeMapper.update(request, reportingCode);

        return reportingCodeMapper.toDto(reportingCodeRepository.save(reportingCode));
    }

    public void delete(Long id) {
        ReportingCode reportingCode = reportingCodeRepository.findById(id)
                .orElseThrow(() -> new ReportingCodeNotFoundException("Reporting code not found"));

        reportingCodeRepository.delete(reportingCode);
    }

}
