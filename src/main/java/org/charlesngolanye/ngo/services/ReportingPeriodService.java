package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingPeriodRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingPeriodRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingPeriodResponseDto;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.entities.ReportingPeriod;
import org.charlesngolanye.ngo.entities.ReportingPeriodStatus;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportingPeriodNotFoundException;
import org.charlesngolanye.ngo.mappers.ReportingPeriodMapper;
import org.charlesngolanye.ngo.repositories.GrantRepository;
import org.charlesngolanye.ngo.repositories.ReportingPeriodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class ReportingPeriodService {
    private final ReportingPeriodRepository reportingPeriodRepository;
    private final GrantRepository grantRepository;
    private final ReportingPeriodMapper reportingPeriodMapper;

    public ReportingPeriodResponseDto create(ReportingPeriodRequestDto requestDto) {
        validateDates(requestDto.getStartDate(), requestDto.getEndDate());

        Grant grant = grantRepository.findById(requestDto.getGrantId())
                .orElseThrow(() -> new GrantNotFoundException("Grant not found with ID: " + requestDto.getGrantId()));

        ReportingPeriod reportingPeriod = reportingPeriodMapper.toEntity(requestDto);
        reportingPeriod.setGrant(grant);

        ReportingPeriod savedReportingPeriod = reportingPeriodRepository.save(reportingPeriod);
        return reportingPeriodMapper.toDto(savedReportingPeriod);
    }

    @Transactional(readOnly = true)
    public ReportingPeriodResponseDto getById(Long id) {
        ReportingPeriod reportingPeriod = reportingPeriodRepository.findById(id)
                .orElseThrow(() -> new ReportingPeriodNotFoundException("Reporting period not found with ID: " + id));
        return reportingPeriodMapper.toDto(reportingPeriod);
    }

    @Transactional(readOnly = true)
    public List<ReportingPeriodResponseDto> getAll() {
        return reportingPeriodMapper.toDtoList(reportingPeriodRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ReportingPeriodResponseDto> getByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        validateDates(startDate, endDate);

        List<ReportingPeriod> periods = reportingPeriodRepository
                .findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);
        return reportingPeriodMapper.toDtoList(periods);
    }

    public ReportingPeriodResponseDto update(Long id, UpdateReportingPeriodRequest request) {
        ReportingPeriod reportingPeriod = reportingPeriodRepository.findById(id)
                .orElseThrow(() -> new ReportingPeriodNotFoundException("Reporting period not found with ID: " + id));

        LocalDate newStartDate = request.getStartDate() != null ? request.getStartDate() : reportingPeriod.getStartDate();
        LocalDate newEndDate = request.getEndDate() != null ? request.getEndDate() : reportingPeriod.getEndDate();
        validateDates(newStartDate, newEndDate);

        reportingPeriodMapper.update(request, reportingPeriod);
        return reportingPeriodMapper.toDto(reportingPeriodRepository.save(reportingPeriod));
    }

    public void close(Long id) {
        ReportingPeriod reportingPeriod = reportingPeriodRepository.findById(id)
                .orElseThrow(() -> new ReportingPeriodNotFoundException("Reporting period not found with ID: " + id));
        reportingPeriod.setStatus(ReportingPeriodStatus.CLOSED);
        reportingPeriodRepository.save(reportingPeriod);

    }

    public void delete(Long id) {
        ReportingPeriod reportingPeriod = reportingPeriodRepository.findById(id)
                .orElseThrow(() -> new ReportingPeriodNotFoundException("Reporting period not found with ID: " + id));

        if (reportingPeriod.getStatus() == ReportingPeriodStatus.CLOSED) {
            throw new IllegalArgumentException("A closed reporting period cannot be deleted");
        }
        reportingPeriodRepository.delete(reportingPeriod);
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}
