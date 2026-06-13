package org.charlesngolanye.ngo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "reference_number")
    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "grant_id")
    private Grant grant;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BudgetCategory budgetCategory;

}
