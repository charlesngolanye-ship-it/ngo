package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @PositiveOrZero
    private Integer displayOrder;

    @NotNull
    private Long templateId;
}
