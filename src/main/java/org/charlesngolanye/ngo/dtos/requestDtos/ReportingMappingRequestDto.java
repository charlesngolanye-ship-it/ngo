package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportingMappingRequestDto {
    @NotNull
    private Long reportTemplateId;

    @NotNull
    private Long budgetCategoryId;

    @NotNull
    private Long reportLineId;
}
