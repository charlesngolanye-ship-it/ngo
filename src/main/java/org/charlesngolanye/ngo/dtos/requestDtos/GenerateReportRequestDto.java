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
public class GenerateReportRequestDto {
    @NotNull
    private Long templateId;

    @NotNull
    private Long reportingPeriodId;
}
