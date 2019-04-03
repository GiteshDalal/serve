package ${service.group.toLowerCase()};

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Serve Engine
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Unauthorized${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public Unauthorized${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException() {
	}

	public Unauthorized${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message) {
		super(message);
	}

	public Unauthorized${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public Unauthorized${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException(Throwable cause) {
		super(cause);
	}
}
