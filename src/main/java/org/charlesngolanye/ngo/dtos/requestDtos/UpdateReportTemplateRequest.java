package org.charlesngolanye.ngo.dtos.requestDtos;

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
public class UpdateReportTemplateRequest {
    private String name;
    private Framework framework;
    private String version;
    private TemplateStatus templateStatus;
}
