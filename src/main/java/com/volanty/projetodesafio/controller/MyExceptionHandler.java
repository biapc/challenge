package com.volanty.projetodesafio.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.management.InvalidAttributeValueException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<String> handleNotFound(Exception ex) {
        String error = ex.getMessage();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	//excecao para parse na data e tipo de enum 
	@ExceptionHandler({IllegalArgumentException.class, ParseException.class})
	public ResponseEntity<String> handleAttributeException(Exception ex) {
		String error = ex.getMessage();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InvalidAttributeValueException.class)
	public void handleAttributeValueException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NO_CONTENT.value());
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void handleConstraintError(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_MODIFIED.value());
    }
}
