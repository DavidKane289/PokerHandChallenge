package org.challenge.exceptions;

public class UnmatchedCardValueException extends Throwable {
    public UnmatchedCardValueException(String value) {
        super(value);
    }
}
