package dk.capworld.cinemautils.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestGlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestGlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        logger.error("handleValidationErrors: " + ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        logger.error("handleConstraintViolation: " + ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv ->
                errors.put(cv.getPropertyPath().toString(), cv.getMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<?> handleBookingException(BookingException e) {
        logger.error("handleBookingException", e);
        if (e.getErrorCode().equals(BookingException.ErrorCode.OBJECT_NOT_FOUND)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BookingException.ErrorCode.OBJECT_NOT_FOUND + " : " + e.getMessage());
        } else
        if (e.getErrorCode().equals(BookingException.ErrorCode.OBJECT_CANNOT_BE_SAVED)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BookingException.ErrorCode.OBJECT_CANNOT_BE_SAVED + " : " + e.getMessage());
        } else {
           return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BookingException.ErrorCode.UNKNOWN_ERROR + " : " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception e) {
        logger.error("handleException", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}