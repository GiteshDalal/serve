package com.giteshdalal.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class TooManyRequestsUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public TooManyRequestsUserServiceException() {
	}

	public TooManyRequestsUserServiceException(String message) {
		super(message);
	}

	public TooManyRequestsUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public TooManyRequestsUserServiceException(Throwable cause) {
		super(cause);
	}
}
