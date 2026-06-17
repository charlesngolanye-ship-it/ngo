package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.ExpenseRequestDto;
import org.charlesngolanye.ngo.dtos.ExpenseResponseDto;
import org.charlesngolanye.ngo.entities.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense toEntity(ExpenseRequestDto requestDto);

    ExpenseResponseDto toDto(Expense expense);
}
