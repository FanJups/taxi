package biz.advanceitgroup.taxirideserver.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.IM_USED)
public class ResourceAlreadyInUseException extends RuntimeException {
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public ResourceAlreadyInUseException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s already in use with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}