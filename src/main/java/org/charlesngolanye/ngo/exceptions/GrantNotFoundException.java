package org.charlesngolanye.ngo.exceptions;

public class GrantNotFoundException extends RuntimeException {
    public GrantNotFoundException(String message) {
        super (message);
    }
}
