package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.ReportingMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportMappingRepository  extends JpaRepository<ReportingMapping, Long> {
    Optional<ReportingMapping> findByReportTemplateIdAndBudgetCategoryId(
            Long reportTemplateId,
            Long budgetCategoryId
    );

    boolean existsByReportTemplateIdAndBudgetCategoryId(
            Long reportTemplateId,
            Long budgetCategoryId
    );


    boolean existsByReportTemplateIdAndBudgetCategoryIdAndIdNot(
            Long templateId, Long budgetCategoryId, Long id
    );
}
