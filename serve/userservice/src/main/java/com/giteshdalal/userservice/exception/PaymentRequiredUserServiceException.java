package com.giteshdalal.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PaymentRequiredUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public PaymentRequiredUserServiceException() {
	}

	public PaymentRequiredUserServiceException(String message) {
		super(message);
	}

	public PaymentRequiredUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentRequiredUserServiceException(Throwable cause) {
		super(cause);
	}
}
