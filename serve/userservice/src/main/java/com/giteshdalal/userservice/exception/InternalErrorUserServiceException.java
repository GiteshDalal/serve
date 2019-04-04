package com.giteshdalal.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public InternalErrorUserServiceException() {
	}

	public InternalErrorUserServiceException(String message) {
		super(message);
	}

	public InternalErrorUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalErrorUserServiceException(Throwable cause) {
		super(cause);
	}
}
