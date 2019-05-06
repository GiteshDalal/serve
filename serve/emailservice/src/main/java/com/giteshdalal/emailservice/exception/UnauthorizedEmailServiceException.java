/**
 * Generated by Serve Engine
 *
 * @author gitesh
 */
package com.giteshdalal.emailservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedEmailServiceException extends RuntimeException {

	private static final long serialVersionUID = 401L;

	public UnauthorizedEmailServiceException() {
	}

	public UnauthorizedEmailServiceException(String message) {
		super(message);
	}

	public UnauthorizedEmailServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedEmailServiceException(Throwable cause) {
		super(cause);
	}
}