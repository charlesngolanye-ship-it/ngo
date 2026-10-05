package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportLineRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportLineRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportLineResponseDto;
import org.charlesngolanye.ngo.entities.ReportLine;
import org.charlesngolanye.ngo.entities.ReportSection;
import org.charlesngolanye.ngo.entities.ReportingCode;
import org.charlesngolanye.ngo.exceptions.ReportLineNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportSectionNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportingCodeNotFoundException;
import org.charlesngolanye.ngo.mappers.ReportLineMapper;
import org.charlesngolanye.ngo.repositories.ReportSectionRepository;
import org.charlesngolanye.ngo.repositories.ReportingCodeRepository;
import org.charlesngolanye.ngo.repositories.ReportingLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportLineService {
    private final ReportingLineRepository reportingLineRepository;
    private final ReportSectionRepository reportSectionRepository;
    private final ReportingCodeRepository reportingCodeRepository;
    private final ReportLineMapper reportLineMapper;

    public ReportLineResponseDto create(ReportLineRequestDto requestDto) {
        ReportSection section = reportSectionRepository.findById(requestDto.getSectionId())
                .orElseThrow(() -> new ReportSectionNotFoundException("Section not found: " + requestDto.getSectionId()));

        ReportingCode code = reportingCodeRepository.findById(requestDto.getReportingCodeId())
                .orElseThrow(() -> new ReportingCodeNotFoundException("Reporting code not found: " + requestDto.getReportingCodeId()));

        section.getTemplate().verifyIsActive();


        if (section.getTemplate().getFramework() != code.getFramework()) {
            throw new IllegalArgumentException(
                    String.format("Cannot assign ReportingCode framework '%s' to ReportSection template framework '%s'",
                            code.getFramework(), section.getTemplate().getFramework())
            );
        }

        
        ReportLine reportLine = reportLineMapper.toEntity(requestDto);
        // Attach fully populated managed entities
        reportLine.setSection(section);
        reportLine.setReportingCode(code);

        return reportLineMapper.toDto(reportingLineRepository.save(reportLine));
    }

    @Transactional(readOnly = true)
    public ReportLineResponseDto getById(Long id) {
        ReportLine reportLine = reportingLineRepository.findById(id)
                .orElseThrow(() -> new ReportLineNotFoundException("Report line not found with ID: " + id));
        return reportLineMapper.toDto(reportLine);
    }

    @Transactional(readOnly = true)
    public List<ReportLineResponseDto> getAll() {
        return reportLineMapper.toDtoList(reportingLineRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ReportLineResponseDto> getBySectionId(Long sectionId) {
        if (sectionId == null) {
            throw new IllegalArgumentException("Section ID must not be null");
        }
        List<ReportLine> reportLines = reportingLineRepository.findBySectionIdOrderByDisplayOrderAsc(sectionId);
        return reportLineMapper.toDtoList(reportLines);
    }

    public ReportLineResponseDto update(Long id, UpdateReportLineRequest request) {
        ReportLine reportLine = reportingLineRepository.findById(id)
                .orElseThrow(() -> new ReportLineNotFoundException("Report line not found with ID: " + id));

        // Verify template active state before applying updates
        reportLine.getReportTemplate().verifyIsActive();

        // Map basic fields (excluding relationships handled manually)
        reportLineMapper.update(request, reportLine);

        // Re-evaluate relationships safely
        Long targetSectionId = request.getSectionId() != null ? request.getSectionId() : reportLine.getSection().getId();
        Long targetCodeId = request.getReportingCodeId() != null ? request.getReportingCodeId() : reportLine.getReportingCode().getId();

        ReportSection section = reportSectionRepository.findById(targetSectionId)
                .orElseThrow(() -> new ReportSectionNotFoundException("Section not found: " + targetSectionId));

        ReportingCode code = reportingCodeRepository.findById(targetCodeId)
                .orElseThrow(() -> new ReportingCodeNotFoundException("Reporting code not found: " + targetCodeId));

        // Check template status again if section changed
        section.getTemplate().verifyIsActive();

        // Cross-validate framework alignment
        if (section.getTemplate().getFramework() != code.getFramework()) {
            throw new IllegalArgumentException(
                    String.format("Cannot assign ReportingCode framework '%s' to ReportSection template framework '%s'",
                            code.getFramework(), section.getTemplate().getFramework())
            );
        }

        reportLine.setSection(section);
        reportLine.setReportingCode(code);

        return reportLineMapper.toDto(reportingLineRepository.save(reportLine));
    }

    public void delete(Long id) {
        ReportLine reportLine = reportingLineRepository.findById(id)
                .orElseThrow(() -> new ReportLineNotFoundException("Report line not found with ID: " + id));

        // Verify template active state before deleting line
        reportLine.getReportTemplate().verifyIsActive();

        reportingLineRepository.delete(reportLine);
    }
}
