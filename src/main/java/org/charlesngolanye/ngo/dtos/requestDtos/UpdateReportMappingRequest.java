package org.charlesngolanye.ngo.dtos.requestDtos;

import lombok.Data;

@Data
public class UpdateReportMappingRequest {
    private Long reportTemplateId;
    private Long budgetCategoryId;
    private Long reportLineId;
}
