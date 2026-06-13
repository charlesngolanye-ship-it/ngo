package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.entities.Expense;
import org.charlesngolanye.ngo.exceptions.ExpenseNotFoundException;
import org.charlesngolanye.ngo.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense){
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id){
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
    }
}
