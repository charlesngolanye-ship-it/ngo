package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.GrantStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrantResponseDto { // Outbound Payload - GET - when a client requests data, the Service and Repository layers should fetch the real domain Entity from the DB but it should be transformed into a Dto before leaving the API
    private Long id;
    private String grantNumber;
    private String grantName;
    private String donorName;
    private BigDecimal totalApprovedBudget;
    private LocalDate startDate;
    private LocalDate endDate;
    private GrantStatus status;
}
