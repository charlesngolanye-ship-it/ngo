package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.ReportLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportLineRepository extends JpaRepository<ReportLine,  Long> {
    List<ReportLine> findBySectionIdOrderByDisplayOrderAsc(
            Long sectionId
    );
}
