package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UnavailableProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public UnavailableProductServiceException() {
	}

	public UnavailableProductServiceException(String message) {
		super(message);
	}

	public UnavailableProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableProductServiceException(Throwable cause) {
		super(cause);
	}
}
