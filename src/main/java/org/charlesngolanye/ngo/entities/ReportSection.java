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
@Table(name = "report_sections",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_template_code",
                columnNames = {
                        "template_id",
                        "code"
                }
        ),
                @UniqueConstraint(
                        name = "uk_template_display_order",
                        columnNames = {
                                "template_id",
                                "display_order"
                        }
                )
        })
public class ReportSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private ReportTemplate template;
}
