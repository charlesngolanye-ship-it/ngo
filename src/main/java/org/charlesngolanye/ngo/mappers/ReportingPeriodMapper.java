package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ReportingPeriodRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingPeriodRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingPeriodResponseDto;
import org.charlesngolanye.ngo.entities.ReportingPeriod;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface ReportingPeriodMapper {
    ReportingPeriod toEntity(ReportingPeriodRequestDto request);

    ReportingPeriodResponseDto toDto(ReportingPeriod reportingPeriod);

    List<ReportingPeriodResponseDto> toDtoList(List<ReportingPeriod> reportingPeriods);

    void update(UpdateReportingPeriodRequest request, @MappingTarget ReportingPeriod reportingPeriod);
}
