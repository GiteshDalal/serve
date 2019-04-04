package com.giteshdalal.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UnavailableUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public UnavailableUserServiceException() {
	}

	public UnavailableUserServiceException(String message) {
		super(message);
	}

	public UnavailableUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableUserServiceException(Throwable cause) {
		super(cause);
	}
}
