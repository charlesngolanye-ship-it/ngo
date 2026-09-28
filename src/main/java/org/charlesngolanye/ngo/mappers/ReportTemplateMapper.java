package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportTemplateRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportTemplateRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportTemplateResponseDto;
import org.charlesngolanye.ngo.entities.ReportTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportTemplateMapper {
    ReportTemplate toEntity(ReportTemplateRequestDto request);

    ReportTemplateResponseDto toDto(ReportTemplate reportTemplate);

    List<ReportTemplateResponseDto> toDtoList(List<ReportTemplate> reportTemplates);

    void update(UpdateReportTemplateRequest request, @MappingTarget ReportTemplate reportTemplate);
}
