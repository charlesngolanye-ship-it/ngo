package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingPeriodRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.GrantResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateGrantRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingPeriodResponseDto;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.entities.ReportingPeriod;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.exceptions.ReportingPeriodNotFoundException;
import org.charlesngolanye.ngo.mappers.GrantMapper;
import org.charlesngolanye.ngo.mappers.ReportingPeriodMapper;
import org.charlesngolanye.ngo.repositories.GrantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor// only entities with all args + no args
@Transactional
public class GrantService{
    private final GrantRepository grantRepository;
    private final GrantMapper grantMapper;
    private final ReportingPeriodMapper reportingPeriodMapper;

    public GrantResponseDto addGrant(GrantRequestDto grantRequestDto){
        Grant grant = grantMapper.toEntity(grantRequestDto);

        validateGrantDates(grant);

        Grant savedGrant = grantRepository.save(grant);
        return grantMapper.toDto(savedGrant);
    }

    @Transactional(readOnly = true)
    public List<GrantResponseDto> getAllGrants() {
        return grantRepository.findAll()
                .stream()
                .map(grantMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GrantResponseDto getGrantById(Long id) {
        Grant grant = grantRepository.findById(id)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));

        return grantMapper.toDto(grant);
    }

    @Transactional
    public GrantResponseDto updateGrant(Long id, UpdateGrantRequest request) {
        Grant grant = grantRepository.findById(id)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));
        grantMapper.update(request, grant);
        validateGrantDates(grant); // Ensure dates are still valid after client updates them

        return grantMapper.toDto(grantRepository.save(grant));
    }

    public void deleteGrant(Long id) {
        Grant grant = grantRepository.findById(id)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));

       grantRepository.delete(grant);
    }

    private void validateGrantDates(Grant grant) {
        if (grant.getEndDate() != null && grant.getStartDate() != null
            && grant.getEndDate().isBefore(grant.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    /**
     * Adds a ReportingPeriod to a Grant using the helper method.
     */
    public ReportingPeriodResponseDto addReportingPeriod(Long grantId, ReportingPeriodRequestDto request) {
        Grant grant = grantRepository.findById(grantId)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found with id: " + grantId));

        ReportingPeriod period = reportingPeriodMapper.toEntity(request);

        // 1. USE HELPER METHOD: Syncs both grant.getReportingPeriods() AND period.setGrant()
        grant.addReportingPeriod(period);

        // 2. Save parent entity. CascadeType.PERSIST / MERGE saves the child automatically.
        // Because @Transactional is active, changes auto-flush upon method return.
        return reportingPeriodMapper.toDto(period);
    }

    /**
     * Removes a ReportingPeriod from a Grant using the helper method.
     */
    public void removeReportingPeriod(Long grantId, Long periodId) {
        Grant grant = grantRepository.findById(grantId)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found with id: " + grantId));

        ReportingPeriod periodToRemove = grant.getReportingPeriods().stream()
                .filter(p -> p.getId().equals(periodId))
                .findFirst()
                .orElseThrow(() -> new ReportingPeriodNotFoundException("Reporting period not found with id: " + periodId));

        // 1. USE HELPER METHOD: Dissociates period from grant and removes from list
        grant.removeReportingPeriod(periodToRemove);

        // 2. If orphanRemoval = true is set on Grant.reportingPeriods,
        // removing it from the collection causes Hibernate to issue a DELETE SQL statement automatically.
    }
}
