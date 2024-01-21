package br.com.stoom.exceptions.handler;

import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.stoom.exceptions.NotFoundException;
import br.com.stoom.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> handleBusinessException(final BusinessException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }

    record ApiError(String message, List<String> errors) {

        static ApiError from(final NotFoundException ex) {
            return new ApiError(ex.getMessage(), Collections.emptyList());
        }

        static ApiError from(final BusinessException ex) {
            return new ApiError(ex.getMessage(), ex.getErrors());
        }
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ApiError apiError = new ApiError("constraint violation or duplicate record",
                List.of(ex.getCause().getMessage()));
        return ResponseEntity.status(BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> customValidationErrorHandling(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

}