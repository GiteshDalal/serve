/**
 * Generated by Serve Engine
 *
 * @author gitesh
 */
package com.giteshdalal.emailservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundEmailServiceException extends RuntimeException {

	private static final long serialVersionUID = 404L;

	public NotFoundEmailServiceException() {
	}

	public NotFoundEmailServiceException(String message) {
		super(message);
	}

	public NotFoundEmailServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundEmailServiceException(Throwable cause) {
		super(cause);
	}
}