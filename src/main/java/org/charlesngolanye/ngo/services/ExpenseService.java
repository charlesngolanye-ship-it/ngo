package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ExpenseRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ExpenseResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateExpenseRequest;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.Expense;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.exceptions.ExpenseNotFoundException;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.mappers.ExpenseMapper;
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
    private final ExpenseMapper expenseMapper;

    public ExpenseResponseDto addExpense(ExpenseRequestDto requestDto){
        Expense expense = expenseMapper.toEntity(requestDto);

        Grant verifiedGrant = grantRepository.findById(requestDto.getGrantId())
                .orElseThrow(() -> new GrantNotFoundException("Grant not found")
                );

        BudgetCategory verifiedBudgetCategory = budgetCategoryRepository.findById(requestDto.getBudgetCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Budget category not found"));

        expense.setGrant(verifiedGrant);
        expense.setBudgetCategory(verifiedBudgetCategory);
        validateExpenseDomainRules(expense);

        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.toDto(savedExpense);
    }

    @Transactional(readOnly = true)// use readOnly when retrieving data
    public List<ExpenseResponseDto> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(expenseMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExpenseResponseDto getExpenseById(Long id){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
        return expenseMapper.toDto(expense);
    }

    public ExpenseResponseDto updateExpense(Long id, UpdateExpenseRequest request) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        // Update the values on the managed entity cleanly
        expenseMapper.update(request, existingExpense);

        // Re-run rules to ensure modifications didn't break business invariants
        validateExpenseDomainRules(existingExpense);

        return expenseMapper.toDto(expenseRepository.save(existingExpense));
    }


    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
        expenseRepository.delete(expense);
    }

    private void validateExpenseDomainRules(Expense expense) {
        if (expense.getAmount() == null || expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense amount must be a positive value greater than zero.");
        }

        if(expense.getExpenseDate() == null) {
            throw new IllegalArgumentException("Expense date is required");
        }


        Grant grant = expense.getGrant();
        if (expense.getExpenseDate().isBefore(grant.getStartDate()) ||
                expense.getExpenseDate().isAfter(grant.getEndDate())) {
            throw new IllegalArgumentException("Expense date must be within the grant period ("
            + grant.getStartDate() + " to " + grant.getEndDate() + ")");
        }

    }
}
