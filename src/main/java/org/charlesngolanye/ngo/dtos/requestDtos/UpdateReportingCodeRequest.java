package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.charlesngolanye.ngo.entities.Framework;

@Data
public class UpdateReportingCodeRequest {
    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Framework framework;
}
