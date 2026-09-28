package org.charlesngolanye.ngo.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateReportSectionRequest {
    private String code;
    private String name;
    private Integer displayOrder;
    private Long templateId;
}
