/**
 * Copyright (c) 2025 Your TGTech. All rights reserved.
 * 
 * @file RestExceptionHandler.java
 * @author nikhi
 * @version 1.0
 */
package com.tracking.exception.exceptionhandler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.tracking.dto.ErrorDetails;
import com.tracking.exception.TrackingNumberGenerationException;

/**
 * RestExceptionHandler.java
 */
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(TrackingNumberGenerationException.class)
	public ResponseEntity<ErrorDetails> handleUnApplicationException(TrackingNumberGenerationException ex) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Failed to generate tracking number", ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Missing required query parameter");
		error.put("parameter", ex.getParameterName());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleAllException(Exception ex) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"An unexpected error occurred. Please try again later.", ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
