package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.ExpenseRequestDto;
import org.charlesngolanye.ngo.dtos.ExpenseResponseDto;
import org.charlesngolanye.ngo.entities.Expense;
import org.charlesngolanye.ngo.mappers.ExpenseMapper;
import org.charlesngolanye.ngo.services.ExpenseService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ExpenseResponseDto> addExpense(@RequestBody ExpenseRequestDto requestDto) {
        Expense expense = expenseMapper.toEntity(requestDto);
        Expense savedExpense =  expenseService.addExpense(expense);
        ExpenseResponseDto responseDto = expenseMapper.toDto(savedExpense);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public List<ExpenseResponseDto> getAllExpenses() {
        return expenseService.getAllExpenses()
                .stream()
                .map(expenseMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        ExpenseResponseDto expenseResponseDto = expenseMapper.toDto(expense);
        return ResponseEntity.ok(expenseResponseDto);
    }
}
