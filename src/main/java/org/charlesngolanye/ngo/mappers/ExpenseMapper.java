package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ExpenseRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ExpenseResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateExpenseRequest;
import org.charlesngolanye.ngo.entities.Expense;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ExpenseMapper {
    Expense toEntity(ExpenseRequestDto requestDto);

    @Mapping(source = "budgetCategory.name", target = "budgetCategoryName")
    ExpenseResponseDto toDto(Expense expense);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "grant", ignore = true)
    @Mapping(target = "budgetCategory", ignore = true)
    void update(UpdateExpenseRequest request, @MappingTarget Expense expense);
}
