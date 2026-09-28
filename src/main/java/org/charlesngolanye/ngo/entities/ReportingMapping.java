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
@Table(name = "reporting_mappings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_template_budget_category",
                        columnNames = {"report_template_id", "budget_category_id"}
                )
        }
        )
public class ReportingMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_template_id")
    private ReportTemplate reportTemplate;

    @ManyToOne
    @JoinColumn(name = "budget_category_id")
    private BudgetCategory budgetCategory;

    @ManyToOne
    @JoinColumn(name = "report_line_id")
    private ReportLine reportLine;
}
