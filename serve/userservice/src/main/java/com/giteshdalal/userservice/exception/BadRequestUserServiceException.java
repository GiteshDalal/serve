package com.giteshdalal.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public BadRequestUserServiceException() {
	}

	public BadRequestUserServiceException(String message) {
		super(message);
	}

	public BadRequestUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestUserServiceException(Throwable cause) {
		super(cause);
	}
}
