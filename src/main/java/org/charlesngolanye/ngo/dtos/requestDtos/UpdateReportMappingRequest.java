package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateReportMappingRequest {
    @NotNull
    private Long reportTemplateId;

    @NotNull
    private Long budgetCategoryId;

    @NotNull
    private Long reportLineId;
}
