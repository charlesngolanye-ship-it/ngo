package org.charlesngolanye.ngo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.*;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.mappers.BudgetAllocationMapper;
import org.charlesngolanye.ngo.services.BudgetAllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgetAllocations")
public class BudgetAllocationController {
    private final BudgetAllocationService budgetAllocationService;

    @PostMapping
    public ResponseEntity<BudgetAllocationResponseDto> addBudgetAllocation(
            @Valid @RequestBody BudgetAllocationRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {

        BudgetAllocationResponseDto responseDto = budgetAllocationService.addBudgetAllocation(requestDto);

        var uri = uriBuilder.path("/budgetAllocations/{id}").buildAndExpand(responseDto.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<BudgetAllocationResponseDto>> getBudgetAllocations() {
       return ResponseEntity.ok(budgetAllocationService.getBudgetAllocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetAllocationResponseDto> getBudgetAllocationById(@PathVariable Long id) {
        return ResponseEntity.ok(budgetAllocationService.getBudgetAllocationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetAllocationResponseDto> updateBudgetAllocation(
            @PathVariable (name= "id") Long id,
           @Valid @RequestBody UpdateBudgetAllocationRequest request) {

        BudgetAllocationResponseDto updated = budgetAllocationService.updateBudgetAllocation(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetAllocation(@PathVariable Long id) {
        budgetAllocationService.deleteBudgetAllocation(id);
        return  ResponseEntity.notFound().build();
    }
}

/**
 * according to my chatgpt design
 * POST   /api/budgetallocations/{id}/budgets
 * GET    /api/budgetallocations/{id}/budgets
 *
 * This sub-resource URL design pattern is great when you are working with strict parent-child lifecycles.
 * However, since a BudgetAllocation is a join/relationship record linking a Grant to a Budget Category with an approved amount, the standard REST way to look at this is:
 * If you want to see all budget allocations belonging to a specific Grant, use:
 * GET /grants/{grantId}/budget-allocations
 * If you want to manage allocations as standalone records, stick to your primary endpoint:
 * POST /budgetAllocations and GET /budgetAllocations/{id}
 */
