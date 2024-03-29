package biz.advanceitgroup.taxirideserver.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class TokenRefreshException extends RuntimeException {

	private String token;
	private String message;

	public TokenRefreshException(String token, String message) {
		super(String.format("Couldn't refresh token for [%s]: [%s])", token, message));
		this.token = token;
		this.message = message;
	}
}