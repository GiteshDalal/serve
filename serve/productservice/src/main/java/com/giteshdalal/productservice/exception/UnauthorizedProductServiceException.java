package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public UnauthorizedProductServiceException() {
	}

	public UnauthorizedProductServiceException(String message) {
		super(message);
	}

	public UnauthorizedProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedProductServiceException(Throwable cause) {
		super(cause);
	}
}
