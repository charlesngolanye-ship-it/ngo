package org.charlesngolanye.ngo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.Grant;

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
