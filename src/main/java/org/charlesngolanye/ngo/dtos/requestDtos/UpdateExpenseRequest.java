package org.charlesngolanye.ngo.dtos.requestDtos;

import lombok.Data;
import org.charlesngolanye.ngo.entities.BudgetCategory;
import org.charlesngolanye.ngo.entities.Grant;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class UpdateExpenseRequest {
    private String description;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String vendor;
    private String referenceNumber;
    private Long grantId;
    private Long budgetCategoryId;
}