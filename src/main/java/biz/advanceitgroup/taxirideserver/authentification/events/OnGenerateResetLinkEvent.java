package biz.advanceitgroup.taxirideserver.authentification.events;

import biz.advanceitgroup.taxirideserver.authentification.entities.PasswordResetToken;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class OnGenerateResetLinkEvent extends ApplicationEvent {
	private UriComponentsBuilder redirectUrl;
	private PasswordResetToken passwordResetToken;

	public OnGenerateResetLinkEvent(PasswordResetToken passwordResetToken, UriComponentsBuilder redirectUrl) {
		super(passwordResetToken);
		this.passwordResetToken = passwordResetToken;
		this.redirectUrl = redirectUrl;
	}

	public PasswordResetToken getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public UriComponentsBuilder getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
