package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.ExpenseDto;
import org.charlesngolanye.ngo.entities.Expense;
import org.charlesngolanye.ngo.mappers.ExpenseMapper;
import org.charlesngolanye.ngo.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    @GetMapping
    public List<ExpenseDto> getAllExpenses() {
        return expenseService.getAllExpenses()
                .stream()
                .map(expenseMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        ExpenseDto expenseDto = expenseMapper.toDto(expense);
        return ResponseEntity.ok(expenseDto);
    }
}
