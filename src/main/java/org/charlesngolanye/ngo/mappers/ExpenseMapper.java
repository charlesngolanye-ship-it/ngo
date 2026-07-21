package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.ExpenseRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ExpenseResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateExpenseRequest;
import org.charlesngolanye.ngo.entities.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense toEntity(ExpenseRequestDto requestDto);

    ExpenseResponseDto toDto(Expense expense);

    void update(UpdateExpenseRequest request, @MappingTarget Expense expense);
}
