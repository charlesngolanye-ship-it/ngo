package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetAllocationRepository extends JpaRepository<BudgetAllocation, Long> {

    boolean existsByGrantIdAndBudgetCategoryId(Long grantId, Long budgetCategoryId);
}
