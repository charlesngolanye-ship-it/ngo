package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByGrantIdAndExpenseDateBetween(
            Long grantId,
            LocalDate startDate,
            LocalDate endDate
    );
}
