package br.com.gsividal.o2bproject.controller;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return error;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    IllegalArgumentErrorResponse onIllegalArgumentException(
            IllegalArgumentException e) {
        return new IllegalArgumentErrorResponse(
                IllegalArgumentException.class.toString(), LocalDateTime.now().toString(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintViolationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(cv.getPropertyPath().toString(), cv.getMessage()));
        }

        return error;
    }

    @ExceptionHandler({MalformedJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ValidationErrorResponse onMalformedJwtException(MalformedJwtException malformedJwtException) {

        String msg = malformedJwtException != null ? malformedJwtException.getMessage() : "Could not determine credentials";

        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("Authentication", msg));
        return error;
    }

    @ExceptionHandler({SignatureException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ValidationErrorResponse onSignatureException(SignatureException signatureException) {

        String msg = signatureException != null ? signatureException.getMessage() : "Could not determine credentials";

        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("Authentication", msg));
        return error;
    }
}