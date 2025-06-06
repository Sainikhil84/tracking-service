/**
 * Copyright (c) 2025 Your TGTech. All rights reserved.
 * 
 * @file TrackingNumberGenerationException.java
 * @author nikhi
 * @version 1.0
 */
package com.tracking.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 */
public class TrackingNumberGenerationException extends RuntimeException {

	/**
	 * long
	 * 
	 */
	private static final long serialVersionUID = -5661413187372534798L;

	/**
	 * @param message
	 * @param status
	 */
	public TrackingNumberGenerationException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	/** message **/
	private String message;

	/** status **/
	private HttpStatus status;

}
