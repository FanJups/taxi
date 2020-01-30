package biz.advanceitgroup.taxirideserver.authentification.services.impl;

import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.PasswordResetTokenService;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.UserService;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.InvalidTokenRequestException;
import biz.advanceitgroup.taxirideserver.authentification.entities.PasswordResetToken;
import biz.advanceitgroup.taxirideserver.authentification.repositories.PasswordResetTokenRepository;
import biz.advanceitgroup.taxirideserver.commons.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

	@Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

	@Value("${app.token.password.reset.duration}")
	private Long expiration;

	@Autowired
	UserService userService;


	public PasswordResetToken save(PasswordResetToken passwordResetToken) {
		return passwordResetTokenRepository.save(passwordResetToken);
	}

	public Optional<PasswordResetToken> findByToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	public PasswordResetToken createToken() {
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		String token = Util.generateRandomUuid();
		passwordResetToken.setToken(token);
		passwordResetToken.setExpiryDate(Instant.now().plusMillis(expiration));
		return passwordResetToken;
	}

	public void verifyExpiration(PasswordResetToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			throw new InvalidTokenRequestException("Password Reset Token", token.getToken(),
					"Expired token. Please issue a new request");
		}
	}
}
