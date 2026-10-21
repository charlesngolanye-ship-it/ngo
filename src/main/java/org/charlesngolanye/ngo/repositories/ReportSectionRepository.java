package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.ReportSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportSectionRepository extends JpaRepository<ReportSection, Long> {
    List<ReportSection> findByTemplateIdOrderByDisplayOrderAsc(
            Long templateId
    );

    // use to eliminate call inside
//    @Query("SELECT DISTINCT s FROM ReportSection s LEFT JOIN FETCH s.lines WHERE s.template.id = :templateId ORDER BY s.displayOrder ASC")
//    List<ReportSection> findByTemplateIdWithLines(@Param("templateId") Long templateId);
}
/*
 * Architectural Optimization Tip (N+1 Query Prevention)
* To optimize database access, you can fetch ReportSection entities along with their ReportLine entities in a single query using @EntityGraph or a JOIN FETCH:
* This eliminates the nested call to reportLineRepository.findBySectionIdOrderByDisplayOrderAsc inside the loop
 */
