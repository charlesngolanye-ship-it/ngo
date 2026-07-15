package org.charlesngolanye.ngo.repositories;

import org.charlesngolanye.ngo.entities.Grant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantRepository  extends JpaRepository<Grant, Long> {
}
/*
boolean existsByGrantNumber(String grantNumber);
 */
