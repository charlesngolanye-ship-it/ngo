package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialSummaryResponseDto {
    private BigDecimal totalAllocated;
    private BigDecimal totalSpent;
    private BigDecimal totalRemaining;
    private double overallPercentageSpent;
    private List<CategorySummaryDto> categorySummaries;
}
