package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.responseDtos.CategorySummaryDto;
import org.charlesngolanye.ngo.dtos.responseDtos.FinancialSummaryResponseDto;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.charlesngolanye.ngo.repositories.GrantRepository;
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
    private final GrantRepository grantRepository;

    /**
     * Overall NGO-wide financial summary across all grants.
     */
    public FinancialSummaryResponseDto getOverallFinancialSummary() {
        List<CategorySummaryProjection> projections = budgetCategoryRepository.getCategorySummaries();
        return buildFinancialSummaryDto("All Grants", "ORGANIZATION-WIDE", projections);
    }

    /**
     * Financial summary specific to a single Grant.
     */
    public FinancialSummaryResponseDto getGrantFinancialSummary(Long grantId) {
        Grant grant = grantRepository.findById(grantId)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found with id: " + grantId));

        List<CategorySummaryProjection> projections = budgetCategoryRepository.getCategorySummariesByGrantId(grantId);
        return buildFinancialSummaryDto(grant.getGrantName(), grant.getGrantNumber(), projections);
    }

    /**
     * Helper method to calculate totals and map projections to DTO.
     */
    private FinancialSummaryResponseDto buildFinancialSummaryDto(
            String grantName,
            String grantNumber,
            List<CategorySummaryProjection> projections) {

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
                grantName,
                grantNumber,
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
