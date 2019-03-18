package com.giteshdalal.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestAuthServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public BadRequestAuthServiceException() {
	}

	public BadRequestAuthServiceException(String message) {
		super(message);
	}

	public BadRequestAuthServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestAuthServiceException(Throwable cause) {
		super(cause);
	}
}
