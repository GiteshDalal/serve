package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class RequestTimeoutProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public RequestTimeoutProductServiceException() {
	}

	public RequestTimeoutProductServiceException(String message) {
		super(message);
	}

	public RequestTimeoutProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestTimeoutProductServiceException(Throwable cause) {
		super(cause);
	}
}
