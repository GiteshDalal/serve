package ${service.group.toLowerCase()}.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PaymentRequired${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public PaymentRequired${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException() {
	}

	public PaymentRequired${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message) {
		super(message);
	}

	public PaymentRequired${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentRequired${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(Throwable cause) {
		super(cause);
	}
}
