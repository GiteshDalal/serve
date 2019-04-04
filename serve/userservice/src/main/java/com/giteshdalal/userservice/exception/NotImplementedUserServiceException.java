package com.giteshdalal.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public NotImplementedUserServiceException() {
	}

	public NotImplementedUserServiceException(String message) {
		super(message);
	}

	public NotImplementedUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotImplementedUserServiceException(Throwable cause) {
		super(cause);
	}
}
