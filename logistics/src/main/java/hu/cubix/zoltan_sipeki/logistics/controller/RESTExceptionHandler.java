package hu.cubix.zoltan_sipeki.logistics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import hu.cubix.zoltan_sipeki.logistics.exception.ConstraintViolationException;
import hu.cubix.zoltan_sipeki.logistics.exception.EntityNotFoundException;
import hu.cubix.zoltan_sipeki.logistics.exception.InvalidOperationException;

@RestControllerAdvice
public class RESTExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException e) {
        var violations = e.getViolations();
        var message = new StringBuilder();
        int i = 1;
        int size = violations.size();
        for (var violation : violations) {
            message.append(violation.getPropertyPath());
            message.append(": ");
            message.append(violation.getMessage());
            if (i++ < size) {
                message.append("; ");
            }
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST, message.toString());
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException e) {
        return createErrorResponse(e.getStatusCode(), e.getReason());
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handlEntityNotFoundException(EntityNotFoundException e) {
        return createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handlInvalidOperationException(InvalidOperationException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException e) {
        return createErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
    }

    private ResponseEntity<ProblemDetail> createErrorResponse(HttpStatusCode status, String message) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(status, message)).build();
    }
}
