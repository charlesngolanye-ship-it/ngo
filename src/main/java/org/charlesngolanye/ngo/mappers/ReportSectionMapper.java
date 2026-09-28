package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ReportSectionRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportSectionRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportSectionResponseDto;
import org.charlesngolanye.ngo.entities.ReportSection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportSectionMapper {
    @Mapping(target = "template.id", source = "templateId")
    ReportSection toEntity(ReportSectionRequestDto request);

    @Mapping(target = "templateId", source = "template.id")
    ReportSectionResponseDto toDto(ReportSection reportSection);

    List<ReportSectionResponseDto> toDtoList(List<ReportSection> reportSections);

    @Mapping(target = "template.id", source = "templateId")
    void update(UpdateReportSectionRequest request, @MappingTarget ReportSection reportSection);
}
