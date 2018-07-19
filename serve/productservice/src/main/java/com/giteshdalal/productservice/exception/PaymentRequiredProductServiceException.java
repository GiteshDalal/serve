package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PaymentRequiredProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public PaymentRequiredProductServiceException() {
	}

	public PaymentRequiredProductServiceException(String message) {
		super(message);
	}

	public PaymentRequiredProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentRequiredProductServiceException(Throwable cause) {
		super(cause);
	}
}
