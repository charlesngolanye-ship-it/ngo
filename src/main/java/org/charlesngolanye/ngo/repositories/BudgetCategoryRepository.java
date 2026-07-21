package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.dtos.responseDtos.CategorySummaryDto;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.repositories.projections.CategorySummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @Query("""
        SELECT 
            bc.id AS categoryId, 
            bc.name AS categoryName, 
            (SELECT COALESCE(SUM(ba.approvedAmount), 0) 
             FROM BudgetAllocation ba 
             WHERE ba.budgetCategory = bc) AS allocatedAmount, 
            (SELECT COALESCE(SUM(e.amount), 0) 
             FROM Expense e 
             WHERE e.budgetCategory = bc) AS spentAmount 
        FROM BudgetCategory bc
    """)
    List<CategorySummaryProjection> getCategorySummaries();
}
