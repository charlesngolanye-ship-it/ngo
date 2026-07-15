package org.charlesngolanye.ngo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class GrantRequestDto { //Inbound Payload - POST/ PUT/ PATCH - when a client sends data to application, it should enter as a DTO
    @NotBlank
    private String grantNumber;

    @NotBlank
    private String grantName;

    @NotBlank
    private String donorName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private BigDecimal totalApprovedBudget;
}
