package com.giteshdalal.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 *
 */
@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class RequestTimeoutUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public RequestTimeoutUserServiceException() {
	}

	public RequestTimeoutUserServiceException(String message) {
		super(message);
	}

	public RequestTimeoutUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestTimeoutUserServiceException(Throwable cause) {
		super(cause);
	}
}
