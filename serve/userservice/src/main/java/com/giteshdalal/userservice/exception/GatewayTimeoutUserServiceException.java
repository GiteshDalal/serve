package com.giteshdalal.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeoutUserServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public GatewayTimeoutUserServiceException() {
	}

	public GatewayTimeoutUserServiceException(String message) {
		super(message);
	}

	public GatewayTimeoutUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public GatewayTimeoutUserServiceException(Throwable cause) {
		super(cause);
	}
}
