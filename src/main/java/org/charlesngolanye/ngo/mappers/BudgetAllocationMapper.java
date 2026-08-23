package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.BudgetAllocationRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.BudgetAllocationResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateBudgetAllocationRequest;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface BudgetAllocationMapper {
    BudgetAllocation toEntity(BudgetAllocationRequestDto requestDto);

    @Mapping(source = "grant.id", target = "grantId")
    @Mapping(source = "grant.grantName", target = "grantName")
    @Mapping(source = "budgetCategory.id", target = "budgetCategoryId")
    @Mapping(source = "budgetCategory.name", target = "budgetCategoryName")
    BudgetAllocationResponseDto toDto(BudgetAllocation budgetAllocation);

    void update(UpdateBudgetAllocationRequest request, @MappingTarget BudgetAllocation budgetAllocation);
}
