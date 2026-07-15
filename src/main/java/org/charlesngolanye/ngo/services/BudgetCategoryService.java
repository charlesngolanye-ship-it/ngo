package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.BudgetCategoryRequestDto;
import org.charlesngolanye.ngo.dtos.BudgetCategoryResponseDto;
import org.charlesngolanye.ngo.dtos.UpdateBudgetCategoryRequest;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.mappers.BudgetCategoryMapper;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetCategoryService {

    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetCategoryMapper budgetCategoryMapper;

    public BudgetCategoryResponseDto addBudgetCategory(BudgetCategoryRequestDto requestDto) {

        if (budgetCategoryRepository.existsByNameIgnoreCase(requestDto.getName())) {
            throw new IllegalArgumentException("A budget category with this name already exists.");
        }

        BudgetCategory budgetCategory = budgetCategoryMapper.toEntity(requestDto);
        BudgetCategory savedCategory = budgetCategoryRepository.save(budgetCategory);
        return budgetCategoryMapper.toDto(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<BudgetCategoryResponseDto> getBudgetCategories() {
        return budgetCategoryRepository.findAll().stream()
                .map(budgetCategoryMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BudgetCategoryResponseDto getBudgetCategoryById(Long id) {
        BudgetCategory budgetCategory = budgetCategoryRepository.findById(id)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("BudgetCategory Not Found"));
        return budgetCategoryMapper.toDto(budgetCategory);
    }

    public BudgetCategoryResponseDto updateBudgetCategory(Long id, UpdateBudgetCategoryRequest request) {
        BudgetCategory existingCategory = budgetCategoryRepository.findById(id)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("BudgetCategory Not Found"));

        // Business Check: Ensure update name doesn't clash with an existing category of a different ID
        if (budgetCategoryRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new IllegalArgumentException("Another budget category with this name already exists.");
        }

        // Apply fields to managed entity via mapper
        budgetCategoryMapper.update(request, existingCategory);

        BudgetCategory updatedCategory = budgetCategoryRepository.save(existingCategory);
        return budgetCategoryMapper.toDto(updatedCategory);
    }

    public void deleteBudgetCategory(Long id) {
        BudgetCategory budgetCategory = budgetCategoryRepository.findById(id)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("BudgetCategory Not Found"));
        budgetCategoryRepository.delete(budgetCategory);
    }
}
