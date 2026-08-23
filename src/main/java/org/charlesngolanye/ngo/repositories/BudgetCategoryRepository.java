package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.dtos.responseDtos.CategorySummaryDto;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.repositories.projections.CategorySummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    // 1. NGO-wide summary across all grants
    @Query(value = """
        SELECT 
            bc.id AS categoryId,
            bc.name AS categoryName,
            COALESCE(SUM(ba.approved_amount), 0) AS allocatedAmount,
            COALESCE(SUM(e.amount), 0) AS spentAmount
        FROM budget_categories bc
        LEFT JOIN budget_allocations ba ON bc.id = ba.category_id
        LEFT JOIN expenses e ON bc.id = e.category_id
        GROUP BY bc.id, bc.name
    """, nativeQuery = true)
    List<CategorySummaryProjection> getCategorySummaries();

    // 2. Specific grant summary
    @Query(value = """
        SELECT 
            bc.id AS categoryId,
            bc.name AS categoryName,
            COALESCE(ba.approved_amount, 0) AS allocatedAmount,
            COALESCE(SUM(e.amount), 0) AS spentAmount
        FROM budget_categories bc
        LEFT JOIN budget_allocations ba ON bc.id = ba.category_id AND ba.grant_id = :grantId
        LEFT JOIN expenses e ON bc.id = e.category_id AND e.grant_id = :grantId
        GROUP BY bc.id, bc.name, ba.approved_amount
    """, nativeQuery = true)
    List<CategorySummaryProjection> getCategorySummariesByGrantId(@Param("grantId") Long grantId);

}
