package org.charlesngolanye.ngo.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateGrantRequest {
    private String grantNumber;
    private String grantName;
    private String donorName;
    private BigDecimal totalApprovedBudget;
    private LocalDate startDate;
    private LocalDate endDate;
}
