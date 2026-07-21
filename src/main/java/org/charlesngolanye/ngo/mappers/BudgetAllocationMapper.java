package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.BudgetAllocationRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.BudgetAllocationResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateBudgetAllocationRequest;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BudgetAllocationMapper {
    BudgetAllocation toEntity(BudgetAllocationRequestDto requestDto);

    BudgetAllocationResponseDto toDto(BudgetAllocation budgetAllocation);

    void update(UpdateBudgetAllocationRequest request, @MappingTarget BudgetAllocation budgetAllocation);
}
