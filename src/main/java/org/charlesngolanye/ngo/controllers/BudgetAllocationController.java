package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.BudgetAllocationRequestDto;
import org.charlesngolanye.ngo.dtos.BudgetAllocationResponseDto;
import org.charlesngolanye.ngo.dtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.GrantResponseDto;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.mappers.BudgetAllocationMapper;
import org.charlesngolanye.ngo.services.BudgetAllocationService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<BudgetAllocationResponseDto> addBudgetAllocation(@RequestBody BudgetAllocationRequestDto requestDto) {
        BudgetAllocation budgetAllocation = budgetAllocationMapper.toEntity(requestDto);
        BudgetAllocation savedBudgetAllocation = budgetAllocationService.addBudgetAllocation(budgetAllocation);
        BudgetAllocationResponseDto responseDto = budgetAllocationMapper.toDto(savedBudgetAllocation);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public List<BudgetAllocationResponseDto> getBudgetAllocations() {
       return budgetAllocationService.getBudgetAllocations()
               .stream()
               .map(budgetAllocationMapper::toDto)
               .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetAllocationResponseDto> getBudgetAllocationById(@PathVariable Long id) {
        BudgetAllocation budgetAllocation = budgetAllocationService.getBudgetAllocationById(id);
        BudgetAllocationResponseDto budgetAllocationResponseDto = budgetAllocationMapper.toDto(budgetAllocation);
        return ResponseEntity.ok(budgetAllocationResponseDto);
    }
}
