package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.CalculationType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportLineResponseDto {
    private Long id;
    private String name;
    private Integer displayOrder;
    private CalculationType calculationType;
    private Long sectionId;
    private Long reportingCodeId;
}
