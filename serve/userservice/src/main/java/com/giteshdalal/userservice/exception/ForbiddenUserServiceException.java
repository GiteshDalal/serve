package com.giteshdalal.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public ForbiddenUserServiceException() {
	}

	public ForbiddenUserServiceException(String message) {
		super(message);
	}

	public ForbiddenUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenUserServiceException(Throwable cause) {
		super(cause);
	}
}
