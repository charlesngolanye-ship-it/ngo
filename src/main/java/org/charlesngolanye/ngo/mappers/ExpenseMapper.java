package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.ExpenseDto;
import org.charlesngolanye.ngo.entities.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseDto toDto(Expense expense);
}
