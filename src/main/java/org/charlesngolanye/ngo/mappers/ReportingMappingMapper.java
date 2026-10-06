package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ReportingMappingRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingMappingRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingMappingResponseDto;
import org.charlesngolanye.ngo.entities.ReportingMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportingMappingMapper {

    // Converts an incoming request payload into a DB Entity
    @Mapping(target = "reportTemplate.id", source = "reportTemplateId")
    @Mapping(target = "budgetCategory.id", source = "budgetCategoryId")
    @Mapping(target = "reportLine.id", source = "reportLineId")
     ReportingMapping toEntity(ReportingMappingRequestDto request);

    // Converts a DB Entity into an outgoing response payload
    ReportingMappingResponseDto toDto(ReportingMapping reportingMapping);

    List<ReportingMappingResponseDto> toDtoList(List<ReportingMapping> reportingMappings);

    @Mapping(target = "reportTemplate.id", source = "reportTemplateId")
    @Mapping(target = "budgetCategory.id", source = "budgetCategoryId")
    @Mapping(target = "reportLine.id", source = "reportLineId")
    void update(UpdateReportingMappingRequest request, @MappingTarget ReportingMapping reportingMapping);
}

