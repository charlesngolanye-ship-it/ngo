package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.responseDtos.CategorySummaryDto;
import org.charlesngolanye.ngo.dtos.responseDtos.FinancialSummaryResponseDto;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.charlesngolanye.ngo.repositories.projections.CategorySummaryProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FinancialReportService {
    private final BudgetCategoryRepository budgetCategoryRepository;

    public FinancialSummaryResponseDto getFinancialSummary() {
        List<CategorySummaryProjection> projections = budgetCategoryRepository.getCategorySummaries();

        BigDecimal grandTotalAllocated = BigDecimal.ZERO;
        BigDecimal grandTotalSpent = BigDecimal.ZERO;

        List<CategorySummaryDto> categorySummaries = projections.stream().map(p -> {
            BigDecimal allocated = p.getAllocatedAmount() != null ? p.getAllocatedAmount() : BigDecimal.ZERO;
            BigDecimal spent = p.getSpentAmount() != null ? p.getSpentAmount() : BigDecimal.ZERO;
            BigDecimal remaining = allocated.subtract(spent);

            double percentageSpent = calculatePercentage(spent, allocated);

            return new CategorySummaryDto(
                    p.getCategoryId(),
                    p.getCategoryName(),
                    allocated,
                    spent,
                    remaining,
                    percentageSpent
            );
        }).toList();

        for (CategorySummaryDto category : categorySummaries) {
            grandTotalAllocated = grandTotalAllocated.add(category.getAllocatedAmount());
            grandTotalSpent = grandTotalSpent.add(category.getSpentAmount());
        }

        BigDecimal grandTotalRemaining = grandTotalAllocated.subtract(grandTotalSpent);
        double overallPercentageSpent = calculatePercentage(grandTotalSpent, grandTotalAllocated);

        return new FinancialSummaryResponseDto(
                grandTotalAllocated,
                grandTotalSpent,
                grandTotalRemaining,
                overallPercentageSpent,
                categorySummaries
        );
    }

    private double calculatePercentage(BigDecimal spent, BigDecimal allocated) {
        if (allocated.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return spent.divide(allocated, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }
}
