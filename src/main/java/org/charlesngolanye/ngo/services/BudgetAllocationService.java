package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.charlesngolanye.ngo.exceptions.BudgetAllocationNotFoundException;
import org.charlesngolanye.ngo.repositories.BudgetAllocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetAllocationService {
    private final BudgetAllocationRepository budgetAllocationRepository;

    public BudgetAllocation addBudgetAllocation(BudgetAllocation budgetAllocation) {
        return budgetAllocationRepository.save(budgetAllocation);
    }

    public List<BudgetAllocation>getBudgetAllocations() {
        return budgetAllocationRepository.findAll();
    }

    public BudgetAllocation getBudgetAllocationById(Long id) {
        return budgetAllocationRepository.findById(id)
                .orElseThrow(() -> new BudgetAllocationNotFoundException("BudgetAllocation Not Found"));
    }
}
