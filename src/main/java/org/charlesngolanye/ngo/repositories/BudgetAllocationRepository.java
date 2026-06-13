package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetAllocationRepository extends JpaRepository<BudgetAllocation, Long> {
}
