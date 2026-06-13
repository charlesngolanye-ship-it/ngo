package org.charlesngolanye.ngo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.Grant;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BudgetAllocationDto {
    private Long id;
    private Grant grant;
    private BudgetCategory budgetCategory;
    private BigDecimal approvedAmount;

}
