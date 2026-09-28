package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.Framework;
import org.charlesngolanye.ngo.entities.ReportingCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportingCodeRepository extends JpaRepository<ReportingCode, Long> {
    Optional<ReportingCode> findByFrameworkAndCode(
            Framework framework,
            String code
    );


    boolean existsByFrameworkAndCode(
            Framework framework,
            String code
    );

    List<ReportingCode> findByFramework(
            Framework framework
    );
}
