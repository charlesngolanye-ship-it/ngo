package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.BudgetCategoryRequestDto;
import org.charlesngolanye.ngo.dtos.BudgetCategoryResponseDto;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.mappers.BudgetCategoryMapper;
import org.charlesngolanye.ngo.services.BudgetCategoryService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity <BudgetCategoryResponseDto> addBudgetCategory(@RequestBody BudgetCategoryRequestDto requestDto) {
        BudgetCategory budgetCategory = budgetCategoryMapper.toEntity(requestDto);
        BudgetCategory savedBudgetCategory = budgetCategoryService.addBudgetCategory(budgetCategory);
        BudgetCategoryResponseDto responseDto = budgetCategoryMapper.toDto(savedBudgetCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public List<BudgetCategoryResponseDto> getBudgetCategories() {
        return budgetCategoryService.getBudgetCategories()
                .stream()
                .map(budgetCategoryMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetCategoryResponseDto> getBudgetCategoryById(@PathVariable Long id) {
        BudgetCategory budgetCategory = budgetCategoryService.getBudgetCategoryById(id);
        BudgetCategoryResponseDto budgetCategoryResponseDto = budgetCategoryMapper.toDto(budgetCategory);
        return ResponseEntity.ok(budgetCategoryResponseDto);
    }
}
