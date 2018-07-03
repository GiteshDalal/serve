package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundServeException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public NotFoundServeException() {
	}

	public NotFoundServeException(String message) {
		super(message);
	}

	public NotFoundServeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundServeException(Throwable cause) {
		super(cause);
	}
}
