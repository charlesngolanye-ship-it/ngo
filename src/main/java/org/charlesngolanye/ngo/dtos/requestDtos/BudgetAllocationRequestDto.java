package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetAllocationRequestDto {
    @NotNull(message = "Grant ID is required")
    private Long grantId;

    @NotNull(message = "Budget Category ID is required")
    private Long budgetCategoryId;

    @NotNull(message = "Approved amount is required")
    @Positive(message = "Allocation amount must be positive and greater than zero")
    private BigDecimal approvedAmount;
}
