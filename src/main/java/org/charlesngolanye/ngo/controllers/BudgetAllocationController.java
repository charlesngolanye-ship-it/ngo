package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.BudgetAllocationRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateBudgetAllocationRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.BudgetAllocationResponseDto;
import org.charlesngolanye.ngo.services.BudgetAllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budget-allocations")
@Tag(name = "Budget Allocations")
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

        return  ResponseEntity.noContent().build();
    }
}

// TODO return  ResponseEntity.notFound().build(); -> update in all delete endpoints -> discuss w Ian, as i have a different answer

