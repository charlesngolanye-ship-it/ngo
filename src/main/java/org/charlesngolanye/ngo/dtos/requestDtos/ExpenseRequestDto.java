package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ExpenseRequestDto {
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Expense amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;

    @NotNull(message = "Grant ID is required")
    private Long grantId;// should be grantNumber...better design?

    @NotNull(message = "Budget Category ID is required")
    private Long budgetCategoryId;// Fills the expense category

    @NotNull(message = "Vendor/Payee is required")
    private String vendor; // There are already loaded expenses without vendor

    @NotNull(message = "Invoice/Purchase Order number is required")
    private String referenceNumber; // represents invoice/purchase order etc number
}
