package biz.advanceitgroup.taxirideserver.authentification.services.impl;

import biz.advanceitgroup.taxirideserver.authentification.dto.*;
import biz.advanceitgroup.taxirideserver.authentification.entities.*;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.*;
import biz.advanceitgroup.taxirideserver.authentification.securities.oauth2.user.CustomUserDetails;
import biz.advanceitgroup.taxirideserver.authentification.securities.JwtTokenProvider;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.*;
import biz.advanceitgroup.taxirideserver.commons.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.validation.Valid;

@Service
public class AuthService {

	@Autowired
	private UserService userService;

	@Autowired

	private RoleService roleService;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailVerificationTokenService emailVerificationTokenService;

	@Autowired
	private PhoneVerificationTokenService phoneVerificationTokenService;

	@Autowired
	private PasswordResetTokenService passwordResetTokenService;



    /**
	 * Enregistre un nouvel utilisateur dans la base de données en effectuant une série de vérifications rapides.
	 * @return Un objet utilisateur si créé avec succès
     */
	public Optional<Users> registerUser(RegistrationRequest newRegistrationRequest) {

		String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
		String newRegistrationRequestPhone = newRegistrationRequest.getPhoneNumber();
		
		// Vérifier si le mail existe déjà
		if (emailAlreadyExists(newRegistrationRequestEmail)) {
			throw new ResourceAlreadyInUseException("Email", "Address", newRegistrationRequestEmail);
		}

		// Vérifier si le numéro de téléphone existe déjà
		if (phoneAlreadyExists(newRegistrationRequestPhone)) {
			throw new ResourceAlreadyInUseException("Phone number", "Address", newRegistrationRequestPhone);
		}
		
		System.out.println("Auth service code: "+newRegistrationRequest);

		Users newUser = userService.createUser(newRegistrationRequest);
		Users registeredNewUser = userService.save(newUser);

		return Optional.ofNullable(registeredNewUser);
	}
	
	
	/**
	 * Enregistrement d'un utilisateur par les réseaux sociaux, paramèttre: phone, emil.
	 * si l'email existe, on le connecte avec l'email, si c'est pluston le téléphone, on le connecte avec le téléphone, sinon on
	 * @return Un objet utilisateur si créé avec succès
     */
	
	
public Optional<Users> socialUserregistration(socialRegistrationRequest newRegistrationRequest) {
		
		
		String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
		String newRegistrationRequestPhone = newRegistrationRequest.getPhoneNumber();
		

		// Vérifier si le mail existe déjà
		if (emailAlreadyExists(newRegistrationRequestEmail)) {
			
			return confirmEmailRegistration(newRegistrationRequestEmail);
		}else

		// Vérifier si le numéro de téléphone existe déjà
		if (phoneAlreadyExists(newRegistrationRequestPhone)) {
			//throw new ResourceAlreadyInUseException("Phone number", "Address", newRegistrationRequestPhone);
			
			confirmPhoneRegistrations(newRegistrationRequest.getPhoneNumber(), newRegistrationRequest.getVerificationCode());
			
			Optional<Users> registeredNewUser = null;
			
			return registeredNewUser;
			
		}else {
			Users newUser = userService.createNewSocialUser(newRegistrationRequest);
			Users registeredNewUser = userService.save(newUser);
		
			

			return Optional.ofNullable(registeredNewUser);
		}
		
		
	}


	/**
	 * Vérifie si l'e-mail donné existe déjà dans le référentiel de la base de données ou non
	 * @retour vrai si l'e-mail existe sinon faux
	 */
	public Boolean emailAlreadyExists(String email) {
		return userService.existsByEmail(email);
	}

	/*
	* Vérifier Si le numéro de téléphone existe déjà
	 */
	public Boolean phoneAlreadyExists(String phoneNumber) {
		return userService.existsByPhoneNumber(phoneNumber);
	}

	/**
	 * Authentifier l'utilisateur et le connecter avec un loginRequest
	 */
	public Optional<Authentication> authenticateUser(String username, String password) {
		return Optional.ofNullable(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
				password)));
	}
	
	

	 /**
	  * Confirme la vérification de l'utilisateur en fonction de l'expiration du jeton et marque l'utilisateur comme actif.
	  * Si l'utilisateur est déjà vérifié, enregistrez les appels inutiles à la base de données.
	  */
	public Optional<Users> confirmEmailRegistration(String emailToken) {
		Optional<EmailVerificationToken> emailVerificationTokenOpt =
				emailVerificationTokenService.findByToken(emailToken);
		emailVerificationTokenOpt.orElseThrow(() ->
				new ResourceNotFoundException("Token", "Email verification", emailToken));

		Optional<Users> registeredUserOpt = emailVerificationTokenOpt.map(EmailVerificationToken::getUser);

		Boolean isUserAlreadyVerified = emailVerificationTokenOpt.map(EmailVerificationToken::getUser)
				.map(Users::getIsEmailVerified).filter(Util::isTrue).orElse(false);

		if (isUserAlreadyVerified) {

			return registeredUserOpt;
		}
		emailVerificationTokenOpt.ifPresent(emailVerificationTokenService::verifyExpiration);
		emailVerificationTokenOpt.ifPresent(EmailVerificationToken::confirmStatus);
		emailVerificationTokenOpt.ifPresent(emailVerificationTokenService::save);
		registeredUserOpt.ifPresent(Users::confirmEmailVerification);
		registeredUserOpt.ifPresent(userService::save);
		return registeredUserOpt;
	}


	/**
	 * Confirme la vérification de l'utilisateur en fonction de l'expiration du jeton et marque le téléphone de l'utilisateur vérifié.
	 */
	public String confirmPhoneRegistrations(String phone, String code) {
		
		userService.confirmPhoneRegistrations(phone, code);
		//Optional<Users> registeredUserOpt = phoneVerificationTokenOpt.map(PhoneVerificationToken::getUser);

		
		return "Phone ok";
	}
	public Optional<Users> confirmPhoneRegistration(String phoneToken) {
		Optional<PhoneVerificationToken> phoneVerificationTokenOpt =
				phoneVerificationTokenService.findByToken(phoneToken);
		phoneVerificationTokenOpt.orElseThrow(() ->
				new ResourceNotFoundException("Token", "Phone verification", phoneToken));

		Optional<Users> registeredUserOpt = phoneVerificationTokenOpt.map(PhoneVerificationToken::getUser);

		Boolean isUserAlreadyVerified = phoneVerificationTokenOpt.map(PhoneVerificationToken::getUser)
				.map(Users::getIsPhoneVerified).filter(Util::isTrue).orElse(false);

		if (isUserAlreadyVerified) {
			return registeredUserOpt;
		}
		phoneVerificationTokenOpt.ifPresent(phoneVerificationTokenService::verifyExpiration);
		phoneVerificationTokenOpt.ifPresent(PhoneVerificationToken::confirmStatus);
		phoneVerificationTokenOpt.ifPresent(phoneVerificationTokenService::save);
		registeredUserOpt.ifPresent(Users::confirmPhoneVerification);
		registeredUserOpt.ifPresent(userService::save);
		return registeredUserOpt;
	}
	
	public Optional<Users> sendConfirmPhoneRegistration(String phoneNumber, String code) {
		// TODO Auto-generated method stub
		return null;
	}


	 /**
	  * Essayez de régénérer un nouveau jeton de vérification de courrier électronique avec un
     * jeton expiré précédent. Si le jeton précédent est valide, augmentez son expiration.
	  * sinon mettre à jour la valeur du jeton et ajouter une nouvelle expiration.
     */
	public Optional<EmailVerificationToken> recreateRegistrationToken(String existingToken) {
		Optional<EmailVerificationToken> emailVerificationTokenOpt =
				emailVerificationTokenService.findByToken(existingToken);
		emailVerificationTokenOpt.orElseThrow(() ->
				new ResourceNotFoundException("Token", "Existing email verification", existingToken));
		Boolean userAlreadyVerified =
				emailVerificationTokenOpt.map(EmailVerificationToken::getUser)
						.map(Users::getIsEmailVerified).filter(Util::isTrue).orElse(false);
		if (userAlreadyVerified) {
			return Optional.empty();
		}
		return emailVerificationTokenOpt.map(emailVerificationTokenService::updateExistingTokenWithNameAndExpiry);
	}


	/**
	 * Valide le mot de passe de l'utilisateur actuellement connecté avec le mot de passe donné
	 */
	public Boolean currentPasswordMatches(Users currentUser, String password) {
		return passwordEncoder.matches(password, currentUser.getPassword());
	}


     /**
		* Met à jour le mot de passe de l'utilisateur actuellement connecté
	  */
	public Optional<Users> updatePassword(CustomUserDetails customUserDetails,
										  UpdatePasswordRequest updatePasswordRequest) {
		Users currentUser = userService.getLoggedInUser(customUserDetails.getEmail());

		if (!currentPasswordMatches(currentUser, updatePasswordRequest.getOldPassword())) {

			throw new UpdatePasswordException(currentUser.getEmail(), "Invalid current password");
		}
		String newPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
		currentUser.setPassword(newPassword);
		userService.save(currentUser);
		return Optional.ofNullable(currentUser);
	}


	/**
	 * Génère un jeton JWT pour le client validé
	 */
	public String generateToken(CustomUserDetails customUserDetails) {
		return tokenProvider.generateToken(customUserDetails);
	}


	/**
	 * Génère un jeton JWT pour le client validé par userId
     */

	public String generateTokenFromUserId(Long userId) {
		return tokenProvider.generateTokenFromUserId(userId);
	}
	
	/**
	 * Génère un jeton JWT pour les social user
     */
	/*public String generateSocialToken(socialRegistrationRequest registrationRequest) {
		return tokenProvider.generateTokenForSocialEmail(registrationRequest);
	}*/
	
	/**
	   * Crée un token pour les utilisateur des réseaux sociaux
	   */
	
	public String generateSocialToken(socialRegistrationRequest customUserDetails) {
		return tokenProvider.generateTokenForSocialEmail(customUserDetails);
	}
	
	//Provide token for Social user
	public String createSocialokenUser(socialRegistrationRequest registrationRequest) {
		return tokenProvider.generateTokenForSocialEmail(registrationRequest);
	}
	
	
	
	

	  

      /**
	   * Crée et conserve le jeton d'actualisation pour la machine utilisateur. Si l'appareil existe
	   * déjà, on s'en fiche. Les appareils inutilisés avec des jetons expirés doivent être nettoyés
	   * avec un travail cron. Le jeton généré serait encapsulé dans le jwt.
	   * Supprimez le jeton d'actualisation existant, car l'ancien ne devrait pas rester valide.
	   */

	  public Optional<RefreshToken> createAndPersistRefreshTokenUser(Authentication authentication, Users user
	  ) {
		  Users currentUser = (Users) authentication.getPrincipal();
		  RefreshToken refreshToken = refreshTokenService.createRefreshToken();

		  currentUser.setUser(currentUser);
		  currentUser.setRefreshToken(refreshToken);
		  refreshToken.setUser(currentUser);
		  refreshToken = refreshTokenService.save(refreshToken);
		  refreshTokenService.deleteById(currentUser.getRefreshToken().getId());
		  return Optional.ofNullable(refreshToken);


	  }

      /**
		 * Actualisez le jeton JWT expiré à l'aide d'un jeton d'actualisation et d'informations sur l'appareil. le
	   * * le jeton d'actualisation est mappé sur un périphérique spécifique et, s'il n'est pas expiré, peut aider
	   * * générer un nouveau jwt. Si le jeton d'actualisation est inactif pour un périphérique ou s'il a expiré,
	   * * Jeter les erreurs appropriées.
			*/
	public Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
		//tokenFromDb's device info should match this one
		//Les informations de périphérique de tokenFromDb doivent correspondre à celles-ci
		String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
		Optional<RefreshToken> refreshTokenOpt =
				refreshTokenService.findByToken(requestRefreshToken);
		refreshTokenOpt.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in " +
				"database. Please login again"));

		refreshTokenOpt.ifPresent(refreshTokenService::verifyExpiration);
		refreshTokenOpt.ifPresent(userService::verifyRefreshAvailability);
		refreshTokenOpt.ifPresent(refreshTokenService::increaseCount);
		return refreshTokenOpt.map(RefreshToken::getUser)
				.map(Users::getId).map(this::generateTokenFromUserId);
	}


	/**

	 * Génère un jeton de réinitialisation de mot de passe à partir de la demande de réinitialisation donnée
     */
	public Optional<PasswordResetToken> generatePasswordResetToken(PasswordResetLinkRequest passwordResetLinkRequest) {
		String email = passwordResetLinkRequest.getEmail();
		Optional<Users> userOpt = userService.findByEmail(email);
		userOpt.orElseThrow(() -> new PasswordResetLinkException(email, "No matching user found for the given " +
				"request"));
		PasswordResetToken passwordResetToken = passwordResetTokenService.createToken();
		userOpt.ifPresent(passwordResetToken::setUser);
		passwordResetTokenService.save(passwordResetToken);
		return Optional.ofNullable(passwordResetToken);
	}

	/**
	 * Génération du token pour la vérification du néméro de téléphone
	 */
	public Optional<PhoneVerificationToken> phoneCodeToken(PhoneVerificationRequestDTO dto) {
		String phone = dto.getPhoneNumber();
		Optional<Users> userOpt = userService.findByPhoneNumber(phone);
		userOpt.orElseThrow(() -> new PhoneCodeResetException(phone, "No matching phone found for the given " +
				"request"));
		PhoneVerificationToken phoneToken = phoneVerificationTokenService.createToken();
		userOpt.ifPresent(phoneToken::setUser);
		phoneVerificationTokenService.save(phoneToken);
		return Optional.ofNullable(phoneToken);
	}

	/**
	 * Réinitialiser un mot de passe étant donné une demande de réinitialisation et renvoyer l'utilisateur mis à jour
	 */
	public Optional<Users> resetPassword(PasswordResetRequest passwordResetRequest) {
		String token = passwordResetRequest.getToken();
		Optional<PasswordResetToken> passwordResetTokenOpt = passwordResetTokenService.findByToken(token);
		passwordResetTokenOpt.orElseThrow(() -> new ResourceNotFoundException("Password Reset Token", "Token Id",
				token));

		passwordResetTokenOpt.ifPresent(passwordResetTokenService::verifyExpiration);
		final String encodedPassword = passwordEncoder.encode(passwordResetRequest.getPassword());

		Optional<Users> userOpt = passwordResetTokenOpt.map(PasswordResetToken::getUser);
		userOpt.ifPresent(user -> user.setPassword(encodedPassword));
		userOpt.ifPresent(userService::save);
		return userOpt;
	}
	
	public void modifyRoles(long userId, long roleId){
            roleService.updateRole(userId, roleId);
        }

}