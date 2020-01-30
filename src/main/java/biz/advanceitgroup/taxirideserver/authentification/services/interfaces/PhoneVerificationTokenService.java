package biz.advanceitgroup.taxirideserver.authentification.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.PhoneVerificationToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;

import java.util.Optional;

public interface PhoneVerificationTokenService {

    void createVerificationToken(Users user, String token) ;

    PhoneVerificationToken updateExistingTokenWithNameAndExpiry(PhoneVerificationToken existingToken) ;

    Optional<PhoneVerificationToken> findByToken(String token) ;
    PhoneVerificationToken findByUser(Users user);

    PhoneVerificationToken save(PhoneVerificationToken emailVerificationToken) ;

    String generateNewToken();

    void verifyExpiration(PhoneVerificationToken token) ;

	PhoneVerificationToken createToken();

}
