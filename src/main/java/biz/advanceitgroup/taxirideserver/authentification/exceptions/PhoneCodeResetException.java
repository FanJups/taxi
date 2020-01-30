package biz.advanceitgroup.taxirideserver.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class PhoneCodeResetException extends RuntimeException {

	private String user;
	private String message;

	public PhoneCodeResetException(String user, String message) {
		super(String.format("Couldn't verify phone for [%s]: [%s])", user, message));
		this.user = user;
		this.message = message;
	}
}