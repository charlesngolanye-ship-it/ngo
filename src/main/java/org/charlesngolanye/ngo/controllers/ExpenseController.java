package org.charlesngolanye.ngo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.*;
import org.charlesngolanye.ngo.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;


    @PostMapping
    public ResponseEntity<ExpenseResponseDto> addExpense(
            @Valid @RequestBody ExpenseRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {
        ExpenseResponseDto responseDto = expenseService.addExpense(requestDto);
        var uri = uriBuilder.path("/expenses/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(
            @PathVariable (name= "id") Long id,
            @Valid @RequestBody UpdateExpenseRequest request) {
        return ResponseEntity.ok(expenseService.updateExpense(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return  ResponseEntity.notFound().build();
    }

}

/**
 * according to my chatgpt design the delete and put end points may not be needed
 */