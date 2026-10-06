package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private Integer displayOrder;

    @NotNull
    private CalculationType calculationType;

    @NotNull
    private Long sectionId;

    @NotNull
    private Long reportingCodeId;
}
