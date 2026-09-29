package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class UpdateExpenseRequest {
    @NotBlank
    private String description;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate expenseDate;

    @NotBlank
    private String vendor;

    @NotBlank
    private String referenceNumber;

    @NotNull
    private Long grantId;

    @NotNull
    private Long budgetCategoryId;
}