package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.BudgetCategoryRequestDto;
import org.charlesngolanye.ngo.dtos.BudgetCategoryResponseDto;
import org.charlesngolanye.ngo.dtos.UpdateBudgetCategoryRequest;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BudgetCategoryMapper {
    BudgetCategory toEntity(BudgetCategoryRequestDto requestDto);

    BudgetCategoryResponseDto toDto(BudgetCategory budgetCategory);

    void update(UpdateBudgetCategoryRequest request, @MappingTarget BudgetCategory budgetCategory);
}
