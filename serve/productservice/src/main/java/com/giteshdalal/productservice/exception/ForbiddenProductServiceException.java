package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public ForbiddenProductServiceException() {
	}

	public ForbiddenProductServiceException(String message) {
		super(message);
	}

	public ForbiddenProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenProductServiceException(Throwable cause) {
		super(cause);
	}
}
