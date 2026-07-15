package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.BudgetAllocationRequestDto;
import org.charlesngolanye.ngo.dtos.BudgetAllocationResponseDto;
import org.charlesngolanye.ngo.dtos.UpdateBudgetAllocationRequest;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.exceptions.BudgetAllocationNotFoundException;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.mappers.BudgetAllocationMapper;
import org.charlesngolanye.ngo.repositories.BudgetAllocationRepository;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.charlesngolanye.ngo.repositories.GrantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetAllocationService {

    private final BudgetAllocationRepository budgetAllocationRepository;
    private final GrantRepository grantRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetAllocationMapper budgetAllocationMapper;

    public BudgetAllocationResponseDto addBudgetAllocation(BudgetAllocationRequestDto requestDto) {
        // 1. Convert incoming DTO to entity
        BudgetAllocation budgetAllocation = budgetAllocationMapper.toEntity(requestDto);

        // 2. Fetch and verify related database entities
        Grant verifiedGrant = grantRepository.findById(requestDto.getGrantId())
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));

        BudgetCategory verifiedCategory = budgetCategoryRepository.findById(requestDto.getBudgetCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Budget Category not found"));

        // 3. Reject duplicate allocations for the same Grant + Category combo
        if (budgetAllocationRepository.existsByGrantIdAndBudgetCategoryId(requestDto.getGrantId(), requestDto.getBudgetCategoryId())) {
            throw new IllegalArgumentException("An allocation already exists for this Grant and Budget Category combination.");
        }

        // 4. Attach dependencies & validate business rules
        budgetAllocation.setGrant(verifiedGrant);
        budgetAllocation.setBudgetCategory(verifiedCategory);
        validateAllocationDomainRules(budgetAllocation);

        // 5. Save and return mapped output DTO
        BudgetAllocation savedAllocation = budgetAllocationRepository.save(budgetAllocation);
        return budgetAllocationMapper.toDto(savedAllocation);
    }

    @Transactional(readOnly = true)
    public List<BudgetAllocationResponseDto>getBudgetAllocations() {
        return budgetAllocationRepository.findAll().stream()
                .map(budgetAllocationMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BudgetAllocationResponseDto getBudgetAllocationById(Long id) {
        BudgetAllocation allocation = budgetAllocationRepository.findById(id)
                .orElseThrow(() -> new BudgetAllocationNotFoundException("BudgetAllocation Not Found"));
        return budgetAllocationMapper.toDto(allocation);
    }

    public BudgetAllocationResponseDto updateBudgetAllocation(Long id, UpdateBudgetAllocationRequest request) {
        BudgetAllocation existingAllocation = budgetAllocationRepository.findById(id)
                .orElseThrow(()-> new BudgetAllocationNotFoundException("Budget Allocation Not found"));

        // Load replacement entities if they changed
        Grant verifiedGrant = grantRepository.findById(request.getGrantId())
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));

        BudgetCategory verifiedCategory = budgetCategoryRepository.findById(request.getBudgetCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Budget Category not found"));

        // Check duplicates if attempting to reassign category/grant
        boolean changedRelationship = !existingAllocation.getGrant().getId().equals(request.getGrantId())
                || !existingAllocation.getBudgetCategory().getId().equals(request.getBudgetCategoryId());

        if (changedRelationship && budgetAllocationRepository.existsByGrantIdAndBudgetCategoryId(request.getGrantId(), request.getBudgetCategoryId())) {
            throw new IllegalArgumentException("An allocation already exists for this Grant and Budget Category combination.");
        }

        // Apply fields to managed entity
        budgetAllocationMapper.update(request, existingAllocation);
        existingAllocation.setGrant(verifiedGrant);
        existingAllocation.setBudgetCategory(verifiedCategory);

        validateAllocationDomainRules(existingAllocation);

        return budgetAllocationMapper.toDto(budgetAllocationRepository.save(existingAllocation));
    }

    public void deleteBudgetAllocation(Long id) {
        BudgetAllocation allocation = budgetAllocationRepository.findById(id)
                .orElseThrow(() -> new BudgetAllocationNotFoundException("BudgetAllocation Not Found"));
        budgetAllocationRepository.delete(allocation);
    }

    private void validateAllocationDomainRules(BudgetAllocation budgetAllocation) {
        if (budgetAllocation.getApprovedAmount() == null || budgetAllocation.getApprovedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Allocation amount must be positive value greater than zero.");
        }
    }
}
