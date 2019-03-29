package com.giteshdalal.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundAuthServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public NotFoundAuthServiceException() {
	}

	public NotFoundAuthServiceException(String message) {
		super(message);
	}

	public NotFoundAuthServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundAuthServiceException(Throwable cause) {
		super(cause);
	}
}
