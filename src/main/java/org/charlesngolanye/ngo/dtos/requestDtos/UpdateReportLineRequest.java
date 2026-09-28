package org.charlesngolanye.ngo.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.CalculationType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateReportLineRequest {
    private String name;
    private Integer displayOrder;
    private CalculationType calculationType;
    private Long sectionId;
    private Long reportingCodeId;
}
