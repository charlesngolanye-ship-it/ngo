package org.charlesngolanye.ngo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "budget_allocations")
public class BudgetAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grant_id")
    private Grant grant;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BudgetCategory budgetCategory;

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;

}
