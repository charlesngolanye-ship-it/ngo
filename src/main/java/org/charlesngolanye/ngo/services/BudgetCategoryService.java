package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.exceptions.BudgetCategoryNotFoundException;
import org.charlesngolanye.ngo.repositories.BudgetCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetCategoryService {
    private final BudgetCategoryRepository budgetCategoryRepository;

    public BudgetCategory addBudgetCategory(BudgetCategory budgetCategory) {
        return budgetCategoryRepository.save(budgetCategory);
    }

    public List<BudgetCategory> getBudgetCategories() {
        return budgetCategoryRepository.findAll();
    }

    public BudgetCategory getBudgetCategoryById(Long id) {
        return budgetCategoryRepository.findById(id)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("BudgetCategory Not Found"));
    }
}
