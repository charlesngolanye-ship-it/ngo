package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.ReportingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReportingPeriodRepository extends JpaRepository<ReportingPeriod, Long> {
    List<ReportingPeriod> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(
            LocalDate startDate,
            LocalDate endDate
    );
}
