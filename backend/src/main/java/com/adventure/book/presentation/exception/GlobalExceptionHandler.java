package com.adventure.book.presentation.exception;

import com.adventure.book.domain.exception.BookAlreadyExistsException;
import com.adventure.book.domain.exception.BookNotFoundException;
import com.adventure.book.domain.exception.BusinessRuleViolationException;
import com.adventure.book.domain.exception.SectionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ProblemDetail handleBookNotFound(BookNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        setCommonsProperties(pd, ex.getErrorCode());
        return pd;
    }

    @ExceptionHandler(SectionNotFoundException.class)
    public ProblemDetail handleSectionNotFound(SectionNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        setCommonsProperties(pd, ex.getErrorCode());
        return pd;
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ProblemDetail handleBookAlreadyExist(BookAlreadyExistsException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        setCommonsProperties(pd, ex.getErrorCode());
        return pd;
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ProblemDetail handleBusinessRuleViolation(BusinessRuleViolationException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        setCommonsProperties(pd, ex.getErrorCode());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value",
                        (a, b) -> a
                ));
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        pd.setProperty("fieldErrors", errors);
        setCommonsProperties(pd, "VALIDATION_ERROR");
        return pd;
    }

    private void setCommonsProperties(ProblemDetail pd, String ex) {
        pd.setProperty("errorCode", ex);
        pd.setProperty("timestamp", Instant.now());
    }
}
