package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.BudgetCategoryRequestDto;
import org.charlesngolanye.ngo.dtos.BudgetCategoryResponseDto;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetCategoryMapper {
    BudgetCategory toEntity(BudgetCategoryRequestDto requestDto);

    BudgetCategoryResponseDto toDto(BudgetCategory budgetCategory);
}
