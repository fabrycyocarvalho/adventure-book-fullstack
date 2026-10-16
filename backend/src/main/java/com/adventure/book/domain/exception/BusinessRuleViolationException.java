package com.adventure.book.domain.exception;

public class BusinessRuleViolationException extends DomainException {
    public BusinessRuleViolationException(String errorCode, String message) {
        super(errorCode, message);
    }
}
