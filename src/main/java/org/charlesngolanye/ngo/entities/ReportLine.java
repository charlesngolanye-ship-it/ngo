package org.charlesngolanye.ngo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_lines")
public class ReportLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculation_type")
    private CalculationType calculationType;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private ReportSection section;

    @ManyToOne
    @JoinColumn(name = "reporting_code_id")
    private ReportingCode reportingCode;


    public ReportTemplate getReportTemplate() {
        return this.section != null ? this.section.getTemplate() : null;
    }
}
