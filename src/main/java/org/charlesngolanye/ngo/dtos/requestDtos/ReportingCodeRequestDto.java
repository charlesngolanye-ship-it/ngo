package org.charlesngolanye.ngo.dtos.requestDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.Framework;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportingCodeRequestDto {
    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Framework framework;
}
