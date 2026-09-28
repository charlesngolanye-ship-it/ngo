package org.charlesngolanye.ngo.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.entities.Framework;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportingCodeResponseDto {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Framework framework;
}
