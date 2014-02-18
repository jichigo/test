package xxxxxx.yyyyyy.zzzzzz.app.rest;

import java.util.Locale;

import javax.inject.Inject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Inject
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
            Object body, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        RestError error;
        if (body != null) {
            return super.handleExceptionInternal(ex, body, headers, status,
                    request);
        } else {
            if (HttpStatus.BAD_REQUEST.value() <= status.value()
                    && status.value() < HttpStatus.INTERNAL_SERVER_ERROR
                            .value()) {
                error = createError("e.xx.fw.5000", request.getLocale());
            } else {
                error = createError("e.xx.fw.9001", request.getLocale());
            }
            return super.handleExceptionInternal(ex, error, headers, status,
                    request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        RestError error = createError("e.xx.fw.6500", request.getLocale());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addDetail(createErrorDetail(fieldError, request.getLocale()));
        }
        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            error.addDetail(createErrorDetail(objectError, request.getLocale()));
        }
        return super.handleExceptionInternal(ex, error, headers, status,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        RestError error = createError("e.xx.fw.6500", request.getLocale());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addDetail(createErrorDetail(fieldError, request.getLocale()));
        }
        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            error.addDetail(createErrorDetail(objectError, request.getLocale()));
        }
        return super.handleExceptionInternal(ex, error, headers, status,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        RestError error;
        if (ex.getCause() instanceof JsonParseException) {
            error = createError("e.xx.fw.6502", request.getLocale());
        } else if (ex.getCause() instanceof JsonMappingException) {
            error = createError("e.xx.fw.6501", request.getLocale());
        } else {
            error = createError("e.xx.fw.6000", request.getLocale());
        }
        return super.handleExceptionInternal(ex, error, headers, status,
                request);
    }

    private RestError createError(String code, Locale locale) {
        return new RestError(code, messageSource.getMessage(code, null, locale));
    }

    private RestErrorDetail createErrorDetail(
            DefaultMessageSourceResolvable error, Locale locale) {
        return new RestErrorDetail(error.getCode(), messageSource.getMessage(
                error, locale));
    }

}
