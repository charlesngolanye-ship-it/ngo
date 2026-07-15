package org.charlesngolanye.ngo.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.Grant;

import java.math.BigDecimal;

@Data
public class UpdateBudgetAllocationRequest {
    @NotNull(message = "Grant ID is required")
    private Long grantId;

    @NotNull(message = "Budget Category ID is required")
    private Long budgetCategoryId;

    @NotNull(message = "Approved amount is required")
    @Positive(message = "Allocation amount must be positive and greater than zero")
    private BigDecimal approvedAmount;
}
