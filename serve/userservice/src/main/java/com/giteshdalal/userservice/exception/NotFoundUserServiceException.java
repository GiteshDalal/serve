package com.giteshdalal.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public NotFoundUserServiceException() {
	}

	public NotFoundUserServiceException(String message) {
		super(message);
	}

	public NotFoundUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundUserServiceException(Throwable cause) {
		super(cause);
	}
}
