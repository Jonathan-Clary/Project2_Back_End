package com.revature.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class MyResponseExceptionHandler extends ResponseEntityExceptionHandler {

    // this method gets called when the incoming object(RequestBody) isn't valid and
    // we have @Valid with the @RequestBody
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HashMap<String, String> errors = new HashMap<>();
        for(FieldError err : ex.getFieldErrors()){
            // changing the response to {"field_name": "err message", ...}
            errors.put(err.getField(),err.getDefaultMessage()); // the message that was specified in the model class
        }
        return ResponseEntity.badRequest().body(errors);
    }

}
