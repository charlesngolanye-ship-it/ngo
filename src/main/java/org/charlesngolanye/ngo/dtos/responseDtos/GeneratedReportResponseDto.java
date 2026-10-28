package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.Framework;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneratedReportResponseDto {
    private Long templateId;
    private String templateName;
    private Framework framework;
    private String templateVersion;

    private Long grantId;
    private String grantNumber;
    private String grantName;

    private Long periodId;
    private LocalDate periodStart;
    private LocalDate periodEnd;

   private List<GeneratedReportSectionDto> sections;
}
