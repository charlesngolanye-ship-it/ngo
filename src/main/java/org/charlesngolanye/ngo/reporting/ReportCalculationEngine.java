package org.charlesngolanye.ngo.reporting;

import org.charlesngolanye.ngo.entities.Expense;
import org.charlesngolanye.ngo.entities.ReportLine;
import org.charlesngolanye.ngo.entities.ReportingMapping;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReportCalculationEngine {
    /**
     * Given a specific report line, what is the total sum of all expenses
     * belonging to the budget categories mapped to this report line?
     * @param reportLine entity
     * @param mappings reportingMapping entity list
     * @param expenses entity
     * @return sum of the expenses
     */
    public BigDecimal calculateSum(
            ReportLine reportLine,
            List<ReportingMapping> mappings,
            List<Expense> expenses
    ) {

        Set<Long> categoryIds = mappings.stream()
                .filter(mapping -> mapping.getReportLine().getId()
                        .equals(reportLine.getId()))
                .map(mapping -> mapping.getBudgetCategory().getId())
                .collect(Collectors.toSet());

        return  expenses.stream()
                .filter(expense -> expense.getBudgetCategory() != null && categoryIds.contains(expense.getBudgetCategory().getId()))
                .map(Expense::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
/*
 * Step 1: Extract Relevant Category IDs
 * Filter: Looks through all provided mappings and keeps only those where the mapping's report line ID matches the input reportLine.getId().
 * Map: Extracts the BudgetCategory ID from each matching mapping.
 * Collect: Stores these category IDs in a Set<Long> for O(1) fast lookup performance.
 *
 * Step 2: Filter and Sum Expenses
 * Filter: Keeps only expenses that have a non-null BudgetCategory and whose category ID exists in the categoryIds set extracted in Step 1.
 * Map: Extracts the monetary amount (BigDecimal) from each qualifying Expense object via Expense::getAmount.
 * Filter: Ensures no null amounts are processed to prevent a NullPointerException.
 * Reduce: Starts with a base value of BigDecimal.ZERO and sequentially adds every expense amount together using BigDecimal::add.
 */