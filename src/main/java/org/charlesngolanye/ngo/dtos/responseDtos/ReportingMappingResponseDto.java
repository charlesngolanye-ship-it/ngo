package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.ReportLine;
import org.charlesngolanye.ngo.entities.ReportTemplate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportingMappingResponseDto {
    private Long id;
    private ReportTemplate reportTemplate;
    private BudgetCategory budgetCategory;
    private ReportLine reportLine;
}
