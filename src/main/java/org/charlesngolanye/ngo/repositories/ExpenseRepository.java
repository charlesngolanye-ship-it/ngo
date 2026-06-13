package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
