package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ReportingPeriodRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingPeriodRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingPeriodResponseDto;
import org.charlesngolanye.ngo.entities.ReportingPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ReportingPeriodMapper {
    @Mapping(target = "grant", ignore = true)
    ReportingPeriod toEntity(ReportingPeriodRequestDto request);

    @Mapping(source = "grant.id", target = "grantId")
    ReportingPeriodResponseDto toDto(ReportingPeriod reportingPeriod);

    List<ReportingPeriodResponseDto> toDtoList(List<ReportingPeriod> reportingPeriods);

    @Mapping(target = "grant", ignore = true)
    void update(UpdateReportingPeriodRequest request, @MappingTarget ReportingPeriod reportingPeriod);
}
