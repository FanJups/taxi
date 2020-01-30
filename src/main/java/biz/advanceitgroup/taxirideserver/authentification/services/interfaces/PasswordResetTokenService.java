package biz.advanceitgroup.taxirideserver.authentification.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {

    PasswordResetToken save(PasswordResetToken passwordResetToken) ;

    Optional<PasswordResetToken> findByToken(String token) ;

    PasswordResetToken createToken();

    void verifyExpiration(PasswordResetToken token);
}
