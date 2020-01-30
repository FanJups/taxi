package biz.advanceitgroup.taxirideserver.authentification.events.listeners;

import biz.advanceitgroup.taxirideserver.authentification.entities.PasswordResetToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.MailSendException;
import biz.advanceitgroup.taxirideserver.authentification.services.impl.MailService;
import freemarker.template.TemplateException;
import biz.advanceitgroup.taxirideserver.authentification.events.OnGenerateResetLinkEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnGenerateResetLinkEventListener implements ApplicationListener<OnGenerateResetLinkEvent> {
	@Autowired
	private MailService mailService;



	/**
	 * As soon as a forgot password link is clicked and a valid email id is entered,
	 * Reset password link will be sent to respective mail via this event
	 */
	@Override
	@Async
	public void onApplicationEvent(OnGenerateResetLinkEvent onGenerateResetLinkMailEvent) {
		sendResetLink(onGenerateResetLinkMailEvent);
	}

	/**
	 * Sends Reset Link to the mail address with a password reset link token
	 */
	private void sendResetLink(OnGenerateResetLinkEvent event) {
		PasswordResetToken passwordResetToken = event.getPasswordResetToken();
		Users user = passwordResetToken.getUser();
		String recipientAddress = user.getEmail();
		String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", passwordResetToken.getToken())
				.toUriString();
		try {
			mailService.sendResetLink(emailConfirmationUrl, recipientAddress);
		} catch (IOException | TemplateException | MessagingException e) {

			throw new MailSendException(recipientAddress, "Email Verification");
		}
	}

}
