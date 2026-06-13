package org.charlesngolanye.ngo.exceptions;

public class BudgetCategoryNotFoundException extends RuntimeException {
    public BudgetCategoryNotFoundException(String message) {
        super (message);
    }
}
