package com.energizeglobal.shopping.web.rest.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


    private final MessageSource messageSource;

    private ResponseEntity<Object> getResponseEntity(String message, String detail, HttpStatus httpStatus, Map<String, Object> parameters) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setRequestDateTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()))
                .setDetail(detail)
                .setMessage(message)
                .setStatus(httpStatus)
                .setParameters(parameters);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage());
        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> objectErrors = new HashMap<>();
        Map<String, Object> propertyErrors = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            propertyErrors.put(error.getField(), messageSource.getMessage(error.getDefaultMessage(), null, new Locale("en")));
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            objectErrors.put(error.getObjectName(), messageSource.getMessage(error.getDefaultMessage(), null, new Locale("en")));
        }
        parameters.put("property-error", propertyErrors);
        parameters.put("object-error", objectErrors);
        return getResponseEntity("validation.error", messageSource.getMessage("error.message.validation.parameter", null, new Locale("en")), HttpStatus.BAD_REQUEST, parameters);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage());
        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> objectErrors = new HashMap<>();
        Map<String, Object> propertyErrors = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            propertyErrors.put(error.getField(), error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            objectErrors.put(error.getObjectName(), error.getDefaultMessage());
        }
        parameters.put("property-error", propertyErrors);
        parameters.put("object-error", objectErrors);
        return getResponseEntity("validation.error", messageSource.getMessage("error.message.validation.parameter", null, new Locale("en")), HttpStatus.BAD_REQUEST, parameters);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage());
        return getResponseEntity("type.mismatch.error", "", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        log.error(ex.getMessage());
        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> propertyErrors = new HashMap<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            propertyErrors.put(violation.getRootBeanClass().getName(), violation.getMessage());
        }
        parameters.put("property-error", propertyErrors);
        return getResponseEntity("validation.error", messageSource.getMessage("error.message.validation.parameter", null, new Locale("en")), HttpStatus.BAD_REQUEST, parameters);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(final BadRequestException ex, final WebRequest request) {
        log.error(ex.getMessage());
        return getResponseEntity(ex.getErrorCode(), messageSource.getMessage(ex.getErrorCode(), null, new Locale("en")), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex, final WebRequest request) {
        log.error(ex.getMessage());
        return getResponseEntity(messageSource.getMessage("error.message.access.is.denied", null, new Locale("en")), messageSource.getMessage("error.message.access.is.denied", null, new Locale("en")), HttpStatus.FORBIDDEN, null);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex, final WebRequest request) {
        log.error(ex.getMessage());
        return getResponseEntity(messageSource.getMessage(ex.getErrorCode(), null, new Locale("en")), messageSource.getMessage(ex.getErrorCode(), null, new Locale("en")), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.error(ex.getMessage());
        return getResponseEntity(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
