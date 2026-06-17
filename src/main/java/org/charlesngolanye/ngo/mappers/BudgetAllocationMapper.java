package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.BudgetAllocationRequestDto;
import org.charlesngolanye.ngo.dtos.BudgetAllocationResponseDto;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetAllocationMapper {
    BudgetAllocation toEntity(BudgetAllocationRequestDto requestDto);

    BudgetAllocationResponseDto toDto(BudgetAllocation budgetAllocation);
}
