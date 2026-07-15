package org.charlesngolanye.ngo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.*;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.mappers.BudgetCategoryMapper;
import org.charlesngolanye.ngo.services.BudgetCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgetCategories")
public class BudgetCategoryController {
    private final BudgetCategoryService budgetCategoryService;

    @PostMapping
    public ResponseEntity <BudgetCategoryResponseDto> addBudgetCategory(
           @Valid @RequestBody BudgetCategoryRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {

        BudgetCategoryResponseDto responseDto = budgetCategoryService.addBudgetCategory(requestDto);

        var uri = uriBuilder.path("/budgetCategories/{id}").buildAndExpand(responseDto.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<BudgetCategoryResponseDto>> getBudgetCategories() {
        return ResponseEntity.ok(budgetCategoryService.getBudgetCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetCategoryResponseDto> getBudgetCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(budgetCategoryService.getBudgetCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetCategoryResponseDto> updateBudgetCategory(
            @PathVariable (name= "id") Long id,
            @Valid @RequestBody UpdateBudgetCategoryRequest request) {

        BudgetCategoryResponseDto updated = budgetCategoryService.updateBudgetCategory(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetCategory(@PathVariable Long id) {
        budgetCategoryService.deleteBudgetCategory(id);
        return  ResponseEntity.notFound().build();
    }
}
