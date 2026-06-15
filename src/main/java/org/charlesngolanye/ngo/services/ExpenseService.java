package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.Expense;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.exceptions.ExpenseNotFoundException;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.charlesngolanye.ngo.repositories.ExpenseRepository;
import org.charlesngolanye.ngo.repositories.GrantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final GrantRepository grantRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;

    public Expense addExpense(Expense expense){

        validateExpense(expense);

        Grant verifiedGrant = grantRepository.findById(expense.getGrant().getId())
                .orElseThrow(() -> new GrantNotFoundException("Grant not found")
                );

        if (expense.getExpenseDate().isBefore(verifiedGrant.getStartDate()) || expense.getExpenseDate().isAfter(
                verifiedGrant.getEndDate()
        )) {
            throw new IllegalArgumentException("Expense date must be within the grant period ("
            + verifiedGrant.getStartDate() + " to " + verifiedGrant.getEndDate() + ")");
        }

        BudgetCategory verifiedBudgetCategory = budgetCategoryRepository.findById(expense.getBudgetCategory().getId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Budget category not found"));

        expense.setGrant(verifiedGrant);
        expense.setBudgetCategory(verifiedBudgetCategory);

        return expenseRepository.save(expense);
    }

    @Transactional(readOnly = true)
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Expense getExpenseById(Long id){
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
    }

    private void validateExpense(Expense expense) {
        if (expense.getAmount() == null || expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense amount must be a positive value greater than zero.");
        }

        if(expense.getExpenseDate() == null) {
            throw new IllegalArgumentException("Expense date is required");
        }


        if (expense.getGrant() == null || expense.getGrant().getId() == null) {
            throw new IllegalArgumentException("Grant is required");
        }

        if (expense.getBudgetCategory() == null || expense.getBudgetCategory().getId() == null) {
            throw new IllegalArgumentException("Budget category is required");
        }

    }
}
