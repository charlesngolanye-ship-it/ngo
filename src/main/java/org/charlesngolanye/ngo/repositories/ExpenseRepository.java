package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
//    @Query("select e from Expense e where e.referenceNumber = :referenceNumber and e.amount = :amount")
//    List<Expense> findExpenses(@Param("referenceNumber") String referenceNumber, @Param("amount") BigDecimal amount);
}
