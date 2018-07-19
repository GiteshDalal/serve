package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public InternalErrorProductServiceException() {
	}

	public InternalErrorProductServiceException(String message) {
		super(message);
	}

	public InternalErrorProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalErrorProductServiceException(Throwable cause) {
		super(cause);
	}
}
