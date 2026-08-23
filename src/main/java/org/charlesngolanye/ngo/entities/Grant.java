package org.charlesngolanye.ngo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grants")
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment in db
    @Column(name = "id")
    private Long id;

    @Column(name = "grant_number")
    private String grantNumber;

    @Column(name = "grant_name")
    private String grantName;

    @Column(name = "donor_name")
    private String donorName;

    @Column(name = "total_approved_budget")
    private BigDecimal totalApprovedBudget;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GrantStatus status;

    @OneToMany(mappedBy = "grant", cascade = CascadeType.ALL)
    private List<BudgetAllocation> budgetAllocations = new ArrayList<>();

    @OneToMany(mappedBy = "grant", cascade = CascadeType.ALL) // Look at cascade Types...ALL will cascades to all expenses. Look at soft vs hard delete concepts (important for financial systems)
    private List<Expense> expenses = new ArrayList<>();

}

/**
 * One thing I'd challenge before you create all tables:
 * Do you want total_approved_budget stored in the grants table at all?
 * Because it can be calculated from:
 * SUM(budget_allocations.approved_amount)
 * Many financial systems avoid storing totals that can be derived from detail records, because the numbers can drift out of sync. For a real-world design, I'd be tempted to remove total_approved_budget from grants and calculate it from the allocations. That's exactly the kind of design decision architects discuss early in a project.
 *
 * Future improvements to ponder (Mosh)
 * Information Expert Principle -> Assign the responsibility to the class that has the necessary data to do the job
 * Anemic domain -> Classes that only have data (getters/setters)
 * Rich domain -> Classes that have data and behavior(Information Expert Principle)
 */
