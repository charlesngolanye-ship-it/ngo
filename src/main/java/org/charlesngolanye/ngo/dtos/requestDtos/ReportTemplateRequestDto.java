package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.Framework;
import org.charlesngolanye.ngo.entities.TemplateStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportTemplateRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Framework is required")
    private Framework framework;

    @NotBlank(message = "Version is required")
    private String version;

    @NotNull(message = "Template status is required")
    private TemplateStatus templateStatus;
}
