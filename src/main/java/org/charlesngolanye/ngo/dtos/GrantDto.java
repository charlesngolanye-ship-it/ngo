package org.charlesngolanye.ngo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GrantDto {
    private Long id;
    private String grantNumber;
    private String grantName;
    private String donorName;
    private BigDecimal totalApprovedBudget;
}
