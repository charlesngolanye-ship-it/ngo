package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ReportLineRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportLineRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportLineResponseDto;
import org.charlesngolanye.ngo.entities.ReportLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportLineMapper {
    @Mapping(target = "section.id", source = "sectionId")
    @Mapping(target = "reportingCode.id", source = "reportingCodeId")
    ReportLine toEntity(ReportLineRequestDto request);

    @Mapping(target = "sectionId", source = "section.id")
    @Mapping(target = "reportingCodeId", source = "reportingCode.id")
    ReportLineResponseDto toDto(ReportLine reportLine);

    List<ReportLineResponseDto> toDtoList(List<ReportLine> reportLines);

    @Mapping(target = "section.id", source = "sectionId")
    @Mapping(target = "reportingCode.id", source = "reportingCodeId")
    void update(UpdateReportLineRequest request, @MappingTarget ReportLine reportLine);
}
