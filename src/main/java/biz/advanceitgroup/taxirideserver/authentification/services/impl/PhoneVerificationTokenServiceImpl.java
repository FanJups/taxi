package biz.advanceitgroup.taxirideserver.authentification.services.impl;
import biz.advanceitgroup.taxirideserver.authentification.entities.PhoneVerificationToken;
import biz.advanceitgroup.taxirideserver.authentification.enums.TokenStatus;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.InvalidTokenRequestException;
import biz.advanceitgroup.taxirideserver.authentification.repositories.PhoneVerificationTokenRepository;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.PhoneVerificationTokenService;
import biz.advanceitgroup.taxirideserver.commons.helpers.TaxiRideCommonServices;
import biz.advanceitgroup.taxirideserver.commons.helpers.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PhoneVerificationTokenServiceImpl implements PhoneVerificationTokenService {

    @Autowired
    private PhoneVerificationTokenRepository phoneVerificationTokenRepository;
    
    @Value("${app.token.phone.verification.duration}")
	private Long expiration;

    @Autowired
    TaxiRideCommonServices taxiRideCommonServices;

    @Value("${app.token.phone.verification.duration}")
    private Long phoneVerificationTokenExpiryDuration;

    /*
     Créer le code de vérification du numéro de téléphone qui doit être envoyé à l'utilisateur pour activation
     */
    @Override
    public void createVerificationToken(Users user, String token) {
        PhoneVerificationToken phoneVerificationToken = new PhoneVerificationToken();
        phoneVerificationToken.setToken(token);
        phoneVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        phoneVerificationToken.setUser(user);
        phoneVerificationToken.setExpiryDate(Instant.now().plusMillis(phoneVerificationTokenExpiryDuration));
        phoneVerificationTokenRepository.save(phoneVerificationToken);
    }

    
    /*
    Génération d'un token pour la vérification d'un numéro de téléphone
     */
    @Override
    public PhoneVerificationToken createToken() {
    	PhoneVerificationToken phoneToken = new PhoneVerificationToken();
		String token = Util.generateRandomUuid();
		phoneToken.setToken(token);
		phoneToken.setExpiryDate(Instant.now().plusMillis(expiration));
		return phoneToken;
	}
    
    /*
    Mettre à jour un token généré
     */
    @Override
    public PhoneVerificationToken updateExistingTokenWithNameAndExpiry(PhoneVerificationToken existingToken) {
        existingToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        existingToken.setExpiryDate(Instant.now().plusMillis(phoneVerificationTokenExpiryDuration));

        return save(existingToken);
    }

    /*
    Retrouver un code d'activation avec le token donné en paramètre
     */
    @Override
    public Optional<PhoneVerificationToken> findByToken(String token) {
        return phoneVerificationTokenRepository.findByToken(token);
    }

    /*
    Retrouver un code d'activation par utilisateur
     */
    @Override
    public PhoneVerificationToken findByUser(Users user) {
        return phoneVerificationTokenRepository.findByUser(user);
    }

    /*
    Enregistrement d'un Token de vérification du numéro de téléphone
     */
    @Override
    public PhoneVerificationToken save(PhoneVerificationToken phoneVerificationToken) {
        return phoneVerificationTokenRepository.save(phoneVerificationToken);
    }

    /*
    Génération d'un nouveau token contenant 6 chiffres pour la vérification d'un numéro de téléphone
     */
    @Override
    public String generateNewToken() {
        return taxiRideCommonServices.generateRandomDigitsCode(6);
    }

    @Override
    public void verifyExpiration(PhoneVerificationToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenRequestException("Phone Verification Token", token.getToken(),
                    "Expired token. Please issue a new request");
        }
    }
    
    
}
