package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public NotFoundProductServiceException() {
	}

	public NotFoundProductServiceException(String message) {
		super(message);
	}

	public NotFoundProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundProductServiceException(Throwable cause) {
		super(cause);
	}
}
