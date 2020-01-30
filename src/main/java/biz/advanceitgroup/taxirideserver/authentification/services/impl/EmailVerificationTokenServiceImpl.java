package biz.advanceitgroup.taxirideserver.authentification.services.impl;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.EmailVerificationTokenService;
import io.swagger.annotations.ApiParam;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.InvalidTokenRequestException;
import biz.advanceitgroup.taxirideserver.authentification.enums.TokenStatus;
import biz.advanceitgroup.taxirideserver.authentification.entities.EmailVerificationToken;
import biz.advanceitgroup.taxirideserver.authentification.repositories.EmailVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService{

	@Autowired
	private EmailVerificationTokenRepository emailVerificationTokenRepository;

	@Value("${app.token.email.verification.duration}")
	private Long emailVerificationTokenExpiryDuration;

	/**
	 * Create an email verification token and persist it in the database which will be
	 * verified by the user
	 */
	public void createVerificationToken(Users user, String token) {
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
		emailVerificationToken.setToken(token);
		emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
		emailVerificationToken.setUser(user);
		emailVerificationToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));

		emailVerificationTokenRepository.save(emailVerificationToken);
	}


	public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
		existingToken.setTokenStatus(TokenStatus.STATUS_PENDING);
		existingToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));

		return save(existingToken);
	}


	public Optional<EmailVerificationToken> findByToken(String token) {
		return emailVerificationTokenRepository.findByToken(token);
	}


	public EmailVerificationToken findByUser(Users user) {
		return emailVerificationTokenRepository.findByUser(user);
	}


	public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
		return emailVerificationTokenRepository.save(emailVerificationToken);
	}


	public String generateNewToken() {
		return UUID.randomUUID().toString();
	}


	//vérication de l'expiration du token de confirmation du mail
	public void verifyExpiration(EmailVerificationToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			throw new InvalidTokenRequestException("Email Verification Token", token.getToken(),
					"Expired token. Please issue a new request");
			/*HttpHeaders httpHeaders = new HttpHeaders();
			URI txirideweb = null;
			try {
				txirideweb = new URI("https://dev.taxiride.biz/auth/message?token="+token);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			httpHeaders.setLocation(txirideweb);
			
			try {
				openExpireEmailTokenPage(httpHeaders);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			return;*/
		}
	}
	
	public ResponseEntity<?> openExpireEmailTokenPage(HttpHeaders httpHeaders) throws URISyntaxException {
		
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

}
