package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public NotImplementedProductServiceException() {
	}

	public NotImplementedProductServiceException(String message) {
		super(message);
	}

	public NotImplementedProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotImplementedProductServiceException(Throwable cause) {
		super(cause);
	}
}
