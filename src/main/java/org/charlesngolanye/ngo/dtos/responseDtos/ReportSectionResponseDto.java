package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportSectionResponseDto {
    private Long id;
    private String code;
    private String name;
    private Integer displayOrder;
    private Long templateId;
}
