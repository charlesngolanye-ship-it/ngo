package org.charlesngolanye.ngo.exceptions.handler;

import org.charlesngolanye.ngo.exceptions.BudgetAllocationNotFoundException;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.exceptions.ExpenseNotFoundException;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors().forEach( error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Conflict");
        response.put("message", "A grant with this grant number already exists.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(GrantNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGrantNotFound(
            GrantNotFoundException exception
    ) {
        var error = new HashMap<String, String>();
        error.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleExpenseNotFound(
            ExpenseNotFoundException exception
    ) {
        var error = new HashMap<String, String>();
        error.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BudgetCategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBudgetCategoryNotFound(
            BudgetCategoryNotFoundException exception
    ) {
        var error = new HashMap<String, String>();
        error.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BudgetAllocationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBudgetCategoryNotFound(
            BudgetAllocationNotFoundException exception
    ) {
        var error = new HashMap<String, String>();
        error.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
