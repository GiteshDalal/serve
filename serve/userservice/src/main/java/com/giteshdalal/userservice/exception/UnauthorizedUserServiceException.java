package com.giteshdalal.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public UnauthorizedUserServiceException() {
	}

	public UnauthorizedUserServiceException(String message) {
		super(message);
	}

	public UnauthorizedUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedUserServiceException(Throwable cause) {
		super(cause);
	}
}
