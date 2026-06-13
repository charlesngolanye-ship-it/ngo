package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.BudgetCategoryDto;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetCategoryMapper {
    BudgetCategoryDto toDto(BudgetCategory budgetCategory);
}
