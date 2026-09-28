package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ReportMappingRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportMappingRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportMappingResponseDto;
import org.charlesngolanye.ngo.entities.ReportingMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMappingMapper {

    // Converts an incoming request payload into a DB Entity
    @Mapping(target = "reportTemplate.id", source = "reportTemplateId")
    @Mapping(target = "budgetCategory.id", source = "budgetCategoryId")
    @Mapping(target = "reportLine.id", source = "reportLineId")
     ReportingMapping toEntity(ReportMappingRequestDto request);

    // Converts a DB Entity into an outgoing response payload
    ReportMappingResponseDto toDto(ReportingMapping reportingMapping);

    List<ReportMappingResponseDto> toDtoList(List<ReportingMapping> reportingMappings);

    @Mapping(target = "reportTemplate.id", source = "reportTemplateId")
    @Mapping(target = "budgetCategory.id", source = "budgetCategoryId")
    @Mapping(target = "reportLine.id", source = "reportLineId")
    void update(UpdateReportMappingRequest request, @MappingTarget ReportingMapping reportingMapping);
}

