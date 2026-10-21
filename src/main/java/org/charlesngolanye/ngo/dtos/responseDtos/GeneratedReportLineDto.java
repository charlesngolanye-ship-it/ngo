package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.CalculationType;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneratedReportLineDto {
    private Long lineId;
    private String reportingCode;
    private String name;
    private CalculationType calculationType;
    private Integer displayOrder;
    private BigDecimal value;
}
