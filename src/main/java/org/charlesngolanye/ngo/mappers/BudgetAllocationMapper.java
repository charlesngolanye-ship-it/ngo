package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.BudgetAllocationDto;
import org.charlesngolanye.ngo.entities.BudgetAllocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetAllocationMapper {
    BudgetAllocationDto toDto(BudgetAllocation budgetAllocation);
}
