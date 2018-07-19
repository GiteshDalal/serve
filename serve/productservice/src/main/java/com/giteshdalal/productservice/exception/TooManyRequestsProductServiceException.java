package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class TooManyRequestsProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public TooManyRequestsProductServiceException() {
	}

	public TooManyRequestsProductServiceException(String message) {
		super(message);
	}

	public TooManyRequestsProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public TooManyRequestsProductServiceException(Throwable cause) {
		super(cause);
	}
}
