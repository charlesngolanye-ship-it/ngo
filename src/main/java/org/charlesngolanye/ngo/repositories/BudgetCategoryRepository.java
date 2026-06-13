package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
}
