package ${service.group.toLowerCase()}.${service.name.toLowerCase()}service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 *
 */
@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class RequestTimeout${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public RequestTimeout${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException() {
	}

	public RequestTimeout${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message) {
		super(message);
	}

	public RequestTimeout${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestTimeout${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(Throwable cause) {
		super(cause);
	}
}
