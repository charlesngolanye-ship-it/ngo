package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateGrantRequest {
    @NotBlank
    private String grantNumber;

    @NotBlank
    private String grantName;

    @NotBlank
    private String donorName;

    @NotNull
    private BigDecimal totalApprovedBudget;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
