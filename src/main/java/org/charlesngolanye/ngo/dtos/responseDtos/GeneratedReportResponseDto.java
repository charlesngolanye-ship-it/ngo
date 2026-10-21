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
    Long templateId;
    String templateName;
    Framework framework;
    String templateVersion;

    Long grantId;
    String grantNumber;
    String grantName;

    Long periodId;
    LocalDate periodStart;
    LocalDate periodEnd;

    List<GeneratedReportSectionDto> sections;
}
