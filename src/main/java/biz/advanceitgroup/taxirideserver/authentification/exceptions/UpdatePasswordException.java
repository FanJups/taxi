package biz.advanceitgroup.taxirideserver.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UpdatePasswordException extends RuntimeException {

	private String user;
	private String message;

	public UpdatePasswordException(String user, String message) {
		super(String.format("Couldn't update password for [%s]: [%s])", user, message));
		this.user = user;
		this.message = message;
	}
}