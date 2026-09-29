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
@Table(name = "report_lines",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_section_id_display_order",
                columnNames = {"section_id",
                "display_order"
                }
        ),
                @UniqueConstraint(
                        name = "uk_section_id_name",
                        columnNames = {"section_id",
                                "name"
                        }
                )

        })
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private ReportSection section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_code_id")
    private ReportingCode reportingCode;


    public ReportTemplate getReportTemplate() {
        return this.section != null ? this.section.getTemplate() : null;
    }
}
