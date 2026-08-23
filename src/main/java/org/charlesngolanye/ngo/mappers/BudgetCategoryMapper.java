package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.BudgetCategoryRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.BudgetCategoryResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateBudgetCategoryRequest;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface BudgetCategoryMapper {
    BudgetCategory toEntity(BudgetCategoryRequestDto requestDto);

    BudgetCategoryResponseDto toDto(BudgetCategory budgetCategory);

    void update(UpdateBudgetCategoryRequest request, @MappingTarget BudgetCategory budgetCategory);
}
