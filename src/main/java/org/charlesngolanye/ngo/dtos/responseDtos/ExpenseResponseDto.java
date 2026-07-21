package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseResponseDto {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String vendor;
    private String referenceNumber;
}
