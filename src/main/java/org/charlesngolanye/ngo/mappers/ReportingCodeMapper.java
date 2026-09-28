package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingCodeRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingCodeRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingCodeResponseDto;
import org.charlesngolanye.ngo.entities.ReportingCode;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportingCodeMapper {
    // Converts an incoming request payload into a DB Entity
    ReportingCode toEntity(ReportingCodeRequestDto request);

    // Converts a DB Entity into an outgoing response payload
    ReportingCodeResponseDto toDto(ReportingCode reportingCode);

    List<ReportingCodeResponseDto> toDtoList(List<ReportingCode> reportingCodes);

    void update(UpdateReportingCodeRequest request, @MappingTarget ReportingCode reportingCode);
}
