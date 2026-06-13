package org.charlesngolanye.ngo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExpenseDto {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate expenseDate;


}
