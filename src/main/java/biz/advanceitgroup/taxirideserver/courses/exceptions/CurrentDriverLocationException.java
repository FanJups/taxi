package biz.advanceitgroup.taxirideserver.courses.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CurrentDriverLocationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String driverlocation;
	private String message;

	public CurrentDriverLocationException(String driverlocation, String message) {
		super(String.format("Failed to save currnt position [%d] : '%s'", driverlocation, message));
		this.driverlocation = driverlocation;
		this.message = message;
	}
	public CurrentDriverLocationException(String message, Throwable cause) {
		super(message, cause);
	}
}