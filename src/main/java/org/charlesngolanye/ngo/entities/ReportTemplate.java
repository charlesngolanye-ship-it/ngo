package org.charlesngolanye.ngo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.charlesngolanye.ngo.exceptions.InvalidTemplateStateException;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reporting_templates")
public class ReportTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "framework")
    private Framework framework;

    @Column(name = "version")
    private String version;

    @Enumerated(EnumType.STRING)
    @Column(name = "template_status")
    private TemplateStatus templateStatus;


    // Inside ReportTemplate.java
    public void verifyIsActive() {
        if (this.templateStatus == TemplateStatus.CLOSED) {
            throw new InvalidTemplateStateException("Template ID " + this.id + " is CLOSED and cannot be modified.");
        }
    }
}
