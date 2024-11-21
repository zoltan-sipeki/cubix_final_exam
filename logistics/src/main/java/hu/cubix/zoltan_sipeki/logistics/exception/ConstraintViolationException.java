package hu.cubix.zoltan_sipeki.logistics.exception;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

public class ConstraintViolationException extends Exception {
    
    private Set<? extends ConstraintViolation<?>> violations;

    public ConstraintViolationException(Set<? extends ConstraintViolation<?>> violations) {
        this.violations = violations;
    }

    public Set<? extends ConstraintViolation<?>> getViolations() {
        return violations;
    }
}
