package org.charlesngolanye.ngo.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.ReportingPeriodStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateReportingPeriodRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private ReportingPeriodStatus status;
}
