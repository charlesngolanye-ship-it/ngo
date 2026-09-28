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
@Table(
        name = "reporting_codes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_framework_code",
                        columnNames = {"framework", "code"}
                )
        }
)
public class ReportingCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "framework")
    private Framework framework;
}
/**
 * If code is globally unique - eg EU-PERSONNEL, EU-TRAVEL, EU-EQUIPMENT
 * @Column(unique = true)
 * private String code;
 *
 * If codes could be reused across frameworks
 * UNIQUE(framework, code)
 */