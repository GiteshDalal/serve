package com.giteshdalal.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeoutProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public GatewayTimeoutProductServiceException() {
	}

	public GatewayTimeoutProductServiceException(String message) {
		super(message);
	}

	public GatewayTimeoutProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public GatewayTimeoutProductServiceException(Throwable cause) {
		super(cause);
	}
}
