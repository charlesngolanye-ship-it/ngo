package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.Framework;
import org.charlesngolanye.ngo.entities.ReportTemplate;
import org.charlesngolanye.ngo.entities.TemplateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long> {
    List<ReportTemplate> findByFramework(Framework framework);

    List<ReportTemplate> findByTemplateStatus(
            TemplateStatus templateStatus
    );

    List<ReportTemplate> findByFrameworkAndTemplateStatus(
            Framework framework,
            TemplateStatus templateStatus
    );

    // Check for duplicates during creation
    boolean existsByFrameworkAndVersion(Framework framework, String version);

    // Check for duplicates during update (excluding current template ID)
    boolean existsByFrameworkAndVersionAndIdNot(Framework framework, String version, Long id);

    // Dependency check queries for deletion guard
    @Query("SELECT COUNT(s) > 0 FROM Section s WHERE s.reportTemplate.id = :templateId")
    boolean hasChildSections(@Param("templateId") Long templateId);

    @Query("SELECT COUNT(r) > 0 FROM Report r WHERE r.reportTemplate.id = :templateId")
    boolean isUsedInGeneratedReports(@Param("templateId") Long templateId);

}
