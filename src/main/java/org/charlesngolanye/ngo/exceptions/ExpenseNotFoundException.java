package org.charlesngolanye.ngo.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(String message) {
        super (message);
    }
}
