package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.CalculationType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportLineRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;

    @NotNull(message = "Calculation type is required")
    private CalculationType calculationType;

    @NotNull(message = "Section ID is required")
    private Long sectionId;

    @NotNull(message = "Reporting code ID is required")
    private Long reportingCodeId;
}
