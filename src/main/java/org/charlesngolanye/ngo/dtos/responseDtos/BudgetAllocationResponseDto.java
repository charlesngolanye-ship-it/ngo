package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetAllocationResponseDto {
    private Long id;
    private Long grantId;
    private String grantName;
    private Long budgetCategoryId;
    private String budgetCategoryName;
    private BigDecimal approvedAmount;
}
