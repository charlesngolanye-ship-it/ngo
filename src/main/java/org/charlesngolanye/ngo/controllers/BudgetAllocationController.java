package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.BudgetAllocationDto;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.charlesngolanye.ngo.mappers.BudgetAllocationMapper;
import org.charlesngolanye.ngo.services.BudgetAllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgetAllocations")
public class BudgetAllocationController {
    private final BudgetAllocationService budgetAllocationService;
    private final BudgetAllocationMapper budgetAllocationMapper;

    @PostMapping
    public BudgetAllocation addBudgetAllocation(@RequestBody BudgetAllocation budgetAllocation) {
        return budgetAllocationService.addBudgetAllocation(budgetAllocation);
    }

    @GetMapping
    public List<BudgetAllocationDto> getBudgetAllocations() {
       return budgetAllocationService.getBudgetAllocations()
               .stream()
               .map(budgetAllocationMapper::toDto)
               .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetAllocationDto> getBudgetAllocationById(@PathVariable Long id) {
        BudgetAllocation budgetAllocation = budgetAllocationService.getBudgetAllocationById(id);
        BudgetAllocationDto budgetAllocationDto = budgetAllocationMapper.toDto(budgetAllocation);
        return ResponseEntity.ok(budgetAllocationDto);
    }
}
