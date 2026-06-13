package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.BudgetCategoryDto;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.mappers.BudgetCategoryMapper;
import org.charlesngolanye.ngo.services.BudgetCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgetCategories")
public class BudgetCategoryController {
    private final BudgetCategoryService budgetCategoryService;
    private final BudgetCategoryMapper budgetCategoryMapper;

    @PostMapping
    public BudgetCategory addBudgetCategory(@RequestBody BudgetCategory budgetCategory) {
        return budgetCategoryService.addBudgetCategory(budgetCategory);
    }

    @GetMapping
    public List<BudgetCategoryDto> getBudgetCategories() {
        return budgetCategoryService.getBudgetCategories()
                .stream()
                .map(budgetCategoryMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetCategoryDto> getBudgetCategoryById(@PathVariable Long id) {
        BudgetCategory budgetCategory = budgetCategoryService.getBudgetCategoryById(id);
        BudgetCategoryDto budgetCategoryDto = budgetCategoryMapper.toDto(budgetCategory);
        return ResponseEntity.ok(budgetCategoryDto);
    }
}
