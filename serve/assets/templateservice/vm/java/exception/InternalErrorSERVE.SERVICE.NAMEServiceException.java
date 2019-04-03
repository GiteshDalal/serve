package ${service.group.toLowerCase()};

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gitesh
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalError${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public InternalError${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException() {
	}

	public InternalError${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message) {
		super(message);
	}

	public InternalError${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalError${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(Throwable cause) {
		super(cause);
	}
}
