package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public BadRequestProductServiceException() {
	}

	public BadRequestProductServiceException(String message) {
		super(message);
	}

	public BadRequestProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestProductServiceException(Throwable cause) {
		super(cause);
	}
}
