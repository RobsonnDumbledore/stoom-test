package br.com.stoom.exceptions;

import java.util.List;

public class BusinessException extends RuntimeException {

    private List<String> errors;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
