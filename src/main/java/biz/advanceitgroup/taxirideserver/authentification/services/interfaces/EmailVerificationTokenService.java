package biz.advanceitgroup.taxirideserver.authentification.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.entities.EmailVerificationToken;

import java.util.Optional;

public interface EmailVerificationTokenService {

     void createVerificationToken(Users user, String token) ;

     EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) ;

     Optional<EmailVerificationToken> findByToken(String token) ;
     EmailVerificationToken findByUser(Users user);

     EmailVerificationToken save(EmailVerificationToken emailVerificationToken) ;

     String generateNewToken();

     void verifyExpiration(EmailVerificationToken token) ;
}
