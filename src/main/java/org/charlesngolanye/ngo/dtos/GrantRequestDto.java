package org.charlesngolanye.ngo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrantRequestDto { //Inbound Payload - POST/ PUT/ PATCH - when a client sends data to application, it should enter as a DTO
    private String grantName;
    private String donorName;
    private BigDecimal totalApprovedBudget;
}
