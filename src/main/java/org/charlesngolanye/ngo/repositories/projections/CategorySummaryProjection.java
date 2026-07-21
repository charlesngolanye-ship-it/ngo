package org.charlesngolanye.ngo.repositories.projections;

import java.math.BigDecimal;

public interface CategorySummaryProjection {
    Long getCategoryId();
    String getCategoryName();
    BigDecimal getAllocatedAmount();
    BigDecimal getSpentAmount();
}
