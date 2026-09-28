package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.ReportSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportSectionRepository extends JpaRepository<ReportSection, Long> {
    List<ReportSection> findByTemplateIdOrderByDisplayOrderAsc(
            Long templateId
    );
}
