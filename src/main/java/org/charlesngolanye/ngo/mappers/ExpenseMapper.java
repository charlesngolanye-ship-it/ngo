package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.ExpenseRequestDto;
import org.charlesngolanye.ngo.dtos.ExpenseResponseDto;
import org.charlesngolanye.ngo.dtos.UpdateExpenseRequest;
import org.charlesngolanye.ngo.dtos.UpdateGrantRequest;
import org.charlesngolanye.ngo.entities.Expense;
import org.charlesngolanye.ngo.entities.Grant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense toEntity(ExpenseRequestDto requestDto);

    ExpenseResponseDto toDto(Expense expense);

    void update(UpdateExpenseRequest request, @MappingTarget Expense expense);
}
