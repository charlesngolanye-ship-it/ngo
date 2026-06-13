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
    @Column(name = "status")
    private GrantStatus status;

    @OneToMany(mappedBy = "grant", cascade = CascadeType.ALL)
    private List<BudgetAllocation> budgetAllocations = new ArrayList<>();

    @OneToMany(mappedBy = "grant", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();

}
