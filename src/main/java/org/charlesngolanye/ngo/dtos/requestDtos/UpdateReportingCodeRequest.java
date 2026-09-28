package org.charlesngolanye.ngo.dtos.requestDtos;

import lombok.Data;
import org.charlesngolanye.ngo.entities.Framework;

@Data
public class UpdateReportingCodeRequest {
    private String code;
    private String name;
    private String description;
    private Framework framework;
}
