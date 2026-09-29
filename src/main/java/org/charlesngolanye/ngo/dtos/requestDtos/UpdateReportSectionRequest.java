package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateReportSectionRequest {
    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    private Integer displayOrder;

    @NotNull
    private Long templateId;
}
