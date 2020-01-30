package biz.advanceitgroup.taxirideserver.authentification.webs;

import biz.advanceitgroup.taxirideserver.authentification.annotations.CurrentUser;
import biz.advanceitgroup.taxirideserver.authentification.entities.RefreshToken;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.RefreshTokenService;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.UserService;
import biz.advanceitgroup.taxirideserver.authentification.dto.*;
import biz.advanceitgroup.taxirideserver.authentification.entities.EmailVerificationToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.PasswordResetToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.PhoneVerificationToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.Roles;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.enums.AuthProvider;
import biz.advanceitgroup.taxirideserver.authentification.events.OnGenerateResetLinkEvent;
import biz.advanceitgroup.taxirideserver.authentification.events.OnRegenerateEmailVerificationEvent;
import biz.advanceitgroup.taxirideserver.authentification.events.OnUserRegistrationCompleteEvent;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.*;
import biz.advanceitgroup.taxirideserver.authentification.securities.JwtTokenProvider;
import biz.advanceitgroup.taxirideserver.authentification.securities.oauth2.user.CustomUserDetails;
import biz.advanceitgroup.taxirideserver.authentification.services.impl.AuthService;
import biz.advanceitgroup.taxirideserver.commons.entities.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import biz.advanceitgroup.taxirideserver.authentification.events.OnUserAccountChangeEvent;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.RoleService;
import biz.advanceitgroup.taxirideserver.commons.entities.Parameters;
import biz.advanceitgroup.taxirideserver.commons.services.CommonServices;
import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Sms;
import biz.advanceitgroup.taxirideserver.notifications.services.PushNotificationServiceImpl;
import biz.advanceitgroup.taxirideserver.notifications.services.SmsService;
import biz.advanceitgroup.taxirideserver.payements.entities.InternalAccount;
import biz.advanceitgroup.taxirideserver.payements.services.interfaces.PayementService;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Api(value = "Authentication Rest API", description = "Définition des API d'authentification.")

public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private SmsService smsservice;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;
	
	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
        
        @Autowired
        private CommonServices commonServices;
        
        @Autowired
        private PushNotificationServiceImpl pushNotificationServiceImpl;
        
        @Autowired
        private RoleService roleService;
        
        @Autowired
        private PayementService payementService;

	@Value("${app.client.reset.password.path}")
	private String clientResetPasswordPath;


	@ApiOperation(value = "Vérifie si une adresse mail est déjà utilisée.")
	@GetMapping("/checkEmailInUse")
	public ResponseEntity<?> checkEmailInUse(@ApiParam(value = "Email id to check against") @RequestParam("email") String email) {
		Boolean emailExists = authService.emailAlreadyExists(email);
		return ResponseEntity.ok(new ApiResponse(emailExists.toString(), true));
	}

	@ApiOperation(value = "Vérifie que le login (adresse mail ou numéro de téléphone) n'est pas encore utilisé.")
	@GetMapping("/checkUsernameInUse")
	public ResponseEntity<?> checkUsernameInUse(@ApiParam(value = "Username to check against") @RequestParam(
			"username") String username) {
		Boolean usernameExists = authService.emailAlreadyExists(username) || authService.phoneAlreadyExists(username);
		return ResponseEntity.ok(new ApiResponse(usernameExists.toString(), true));
	}

	@ApiOperation(value = "Connecte l'utilisateur sur le système et retrourne les informations sur la connexion y compris le token et les paramètres de base de l'utilisateur.")
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@ApiParam(value = "The LoginRequest dto") @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

		Optional<Authentication> authenticationOpt = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
		authenticationOpt.orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));
		Authentication authentication = authenticationOpt.get();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Optional<RefreshToken> refreshTokenOpt = authService.createAndPersistRefreshTokenUser(authentication,customUserDetails);

		refreshTokenOpt.orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
		String refreshToken = refreshTokenOpt.map(RefreshToken::getToken).get();
		String jwtToken = authService.generateToken(customUserDetails);
                if(loginRequest.getToken()!=null){
                    userService.addPushNotificationsToken(loginRequest.getToken(), customUserDetails.getId());
                
                    pushNotificationServiceImpl.sendPushNotificationToToken(new Notifications("hello","test notifications","hello",loginRequest.getToken()));
                }
                List<Parameters> parameters=commonServices.findAllParameters();
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,
				tokenProvider.getExpiryDuration(), customUserDetails.getUsername(), customUserDetails.getAuthorities(),parameters));
	}
	
	
	
	@SuppressWarnings("null")
	@ApiOperation(value = "Enregistrement d'un utilisateur par un réseau social. Email et Téléphone ")
	@PostMapping("/socialregistration")
	public ResponseEntity<?> socialUserregistration(@ApiParam(value = "The RegistrationRequest dto") @Valid @RequestBody socialRegistrationRequest registrationRequest) {

		
		RegistrationRequest custom = new RegistrationRequest();
		
		System.out.print("Email: "+registrationRequest.getEmail());
		System.out.print("PhoneNumber: "+registrationRequest.getPhoneNumber());
		
		//generate code
		int smscode = new Random().nextInt(900000) + 100000;
		
		custom.setEmail(registrationRequest.getEmail());
		custom.setPhoneNumber(registrationRequest.getPhoneNumber());
		custom.setPassword("Face232Use*r@");
		custom.setVerificationCode(""+smscode);
		custom.setContryCode(registrationRequest.getContryCode());
		
		custom.setProvider(AuthProvider.facebook);
		
		
		String smsphone = registrationRequest.getContryCode()+""+registrationRequest.getPhoneNumber();
		
		//send vérification code by sms
		Sms smsRequest = new Sms();
		
		smsRequest.setPhoneNumber(smsphone);
		smsRequest.setMessage("Verification code: "+smscode);
		
		
		
		System.out.println("Auth control code: "+custom.getVerificationCode());
		
		String useInfoDonne = "Face232Use*r@";
		
		
		if (authService.emailAlreadyExists(registrationRequest.getEmail())) {
			Optional<Authentication> authenticationOpt = authService.authenticateUser(registrationRequest.getEmail(), useInfoDonne);
			authenticationOpt.orElseThrow(() -> new UserLoginException("Couldn't login user"));
			Authentication authentication = authenticationOpt.get();
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

			SecurityContextHolder.getContext().setAuthentication(authentication);
			Optional<RefreshToken> refreshTokenOpt = authService.createAndPersistRefreshTokenUser(authentication,customUserDetails);

			refreshTokenOpt.orElseThrow(() -> new UserLoginException("Couldn't create refresh token for"));
			String refreshToken = refreshTokenOpt.map(RefreshToken::getToken).get();
			String jwtToken = authService.generateToken(customUserDetails);

			return  
					ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,
					tokenProvider.getExpiryDuration(), customUserDetails.getUsername(), customUserDetails.getAuthorities()));
		}
		
		if (authService.phoneAlreadyExists(registrationRequest.getPhoneNumber())) {
			Optional<Authentication> authenticationOpt = authService.authenticateUser(registrationRequest.getPhoneNumber(), useInfoDonne);
			authenticationOpt.orElseThrow(() -> new UserLoginException("Couldn't login user"));
			Authentication authentication = authenticationOpt.get();
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

			SecurityContextHolder.getContext().setAuthentication(authentication);
			Optional<RefreshToken> refreshTokenOpt = authService.createAndPersistRefreshTokenUser(authentication,customUserDetails);

			refreshTokenOpt.orElseThrow(() -> new UserLoginException("Couldn't create refresh token for"));
			String refreshToken = refreshTokenOpt.map(RefreshToken::getToken).get();
			String jwtToken = authService.generateToken(customUserDetails);

			return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,
					tokenProvider.getExpiryDuration(), customUserDetails.getUsername(), customUserDetails.getAuthorities()));
		}
		
		Optional<Users> registeredUserOp = authService.registerUser(custom);
		
		
		Optional<Authentication> authenticationOpt = authService.authenticateUser(registrationRequest.getPhoneNumber(), useInfoDonne);
		authenticationOpt.orElseThrow(() -> new UserLoginException("Couldn't login user"));
		Authentication authentication = authenticationOpt.get();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Optional<RefreshToken> refreshTokenOpt = authService.createAndPersistRefreshTokenUser(authentication,customUserDetails);

		refreshTokenOpt.orElseThrow(() -> new UserLoginException("Couldn't create refresh token for"));
		String refreshToken = refreshTokenOpt.map(RefreshToken::getToken).get();
		String jwtToken = authService.generateToken(customUserDetails);

		
		//send confirmation email		
		registeredUserOp.orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(),
				"Missing user object in database"));
		UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth" +
				"/registerconfirmationbyemail");
		
		// Déclenchement de l'évènement d'envoi des codes de vérification par mail et par téléphone
		OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent =
						new OnUserRegistrationCompleteEvent(registeredUserOp.get(), urlBuilder);
					applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
		

		
		
		//send confirm phone number message
		registeredUserOp.orElseThrow(() -> new UserRegistrationException(registrationRequest.getPhoneNumber(),
				"Missing user object in database"));
		UriComponentsBuilder urlBuilder2 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth" +
				"/registerconfirmationbyphone");
		
		
		OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent2 =
				new OnUserRegistrationCompleteEvent(registeredUserOp.get(), urlBuilder2);
			applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent2);
			
		
		
		PhoneVerificationRequestDTO phoneverifiy = new PhoneVerificationRequestDTO();
		phoneverifiy.setPhoneNumber(registrationRequest.getPhoneNumber());
		phoneverifiy.setCode(""+smscode);
		
		registerphonenumberconfirmation(phoneverifiy);
		
		
					
		//smsservice.sendSmsCode(smsRequest);
			
			
		
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,
				tokenProvider.getExpiryDuration(), customUserDetails.getUsername(), customUserDetails.getAuthorities(), registerphonenumberconfirmation(phoneverifiy)));
		
		//return ResponseEntity.ok(new ApiResponse("Users registered successfully.", true));
		
	}
	
	/*@ApiOperation(value = "send verification code by sms to phone number  ")
    @PostMapping("smscode")
    public void sendSms(@Valid @RequestBody Sms smsRequest) {
		
		
		smsRequest.setFromphoneNumber("");
		smsRequest.setTophoneNumber(smsRequest.getToPhoneNumber());
		smsRequest.setMessage(smsRequest.getMessage());
		
		
		smsservice.sendSmsCode(smsRequest);
    }*/
	
	@ApiOperation(value = "Send sms ")
    @PostMapping("sendsms")
    public String sendSmsCode(@Valid @RequestBody Sms smsRequest) {
		
		
	
		smsRequest.setPhoneNumber(smsRequest.getPhoneNumber());
		smsRequest.setMessage(smsRequest.getMessage());
		
		
		return smsservice.sendSmsCode(smsRequest);
    }
	
	@SuppressWarnings("null")
	@ApiOperation(value = "Enregistre un utilisateur et déclenche l'évènement d'envoi des codes de vérification par mail et par téléphone.")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@ApiParam(value = "The RegistrationRequest dto") @Valid @RequestBody RegistrationRequest registrationRequest) {
		
		//generate code
		int smscode = new Random().nextInt(900000) + 100000;
		
		registrationRequest.setVerificationCode(""+smscode);
		
		
		String smsphone = registrationRequest.getContryCode()+""+registrationRequest.getPhoneNumber();
		
		//send vérification code by sms
		Sms smsRequest = new Sms();
		
		smsRequest.setPhoneNumber(smsphone);
		smsRequest.setMessage("Verification code: "+smscode);
		
		//smsservice.sendSmsCode(smsRequest);
		
		
		Optional<Users> registeredUserOpt = authService.registerUser(registrationRequest);
		
		System.out.println("Auth control code: "+registrationRequest.getVerificationCode());

		// Génération et envoi du code d'activation au mail de l'utilisateur au cas où le mail est fourni
		registeredUserOpt.orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(),
				"Missing user object in database"));
		UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth" +
				"/registerconfirmationbyemail");

		// Déclenchement de l'évènement d'envoi des codes de vérification par mail et par téléphone
		OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent =
				new OnUserRegistrationCompleteEvent(registeredUserOpt.get(), urlBuilder);
		applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                
		//generate code
		//int smscode = new Random().nextInt(900000) + 100000;
				
		//envois d'un code de vérification par sms
		PhoneVerificationRequestDTO phoneverifiy = new PhoneVerificationRequestDTO();
		phoneverifiy.setPhoneNumber(registrationRequest.getPhoneNumber());
		phoneverifiy.setCode(""+smscode);
		
		registerphonenumberconfirmation(phoneverifiy);
		
		return ResponseEntity.ok(registerphonenumberconfirmation(phoneverifiy)); 
		

		//return ResponseEntity.ok(new ApiResponse("Users registered successfully. Check your email" +
		//		" for verification", true));
	}


	@ApiOperation(value = "Génère le lien de réinitialisation du mot de passe et déclenche l'évènement de l'envoi de mail contenant le lien " +
			"de réinitialisation du mot de passe.")
	@PostMapping("/password/resetlink")
	public ResponseEntity<?> resetLink(@ApiParam(value = "The PasswordResetLinkRequest dto") @Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest, HttpServletRequest request) {

		// Récupérer l'url d'origine avec laquelle construire le lien de réinitialisation du mot de passe
		//String clientUrl = request.getHeader("origin");
		String clientUrl = "";

		Optional<PasswordResetToken> passwordResetTokenOpt = authService
				.generatePasswordResetToken(passwordResetLinkRequest);
		passwordResetTokenOpt.orElseThrow(() -> new PasswordResetLinkException(passwordResetLinkRequest.getEmail(),
				"Couldn't create a valid token"));
		PasswordResetToken passwordResetToken = passwordResetTokenOpt.get();
		//UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromHttpUrl(clientUrl + clientResetPasswordPath);
		UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromHttpUrl("https://dev.taxiride.biz/auth/reset-password");
		
		OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken,
				urlBuilder);
		applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
		return ResponseEntity.ok(new ApiResponse("Password reset link sent successfully", true));
	}
	
	@ApiOperation(value = "Génère et envois un code de confirmation du numéro de téléphone à l'utilisateur.")
	@PostMapping("/registerphonenumberconfirmation")
	public ResponseEntity<?> registerphonenumberconfirmation(@Valid @RequestBody PhoneVerificationRequestDTO phoneVerificationRequestDTO) {
		//Optional<Users> verifiedUserOpt = authService.sendConfirmPhoneRegistration(phoneVerificationRequestDTO.getPhoneNumber(), phoneVerificationRequestDTO.getCode());
		//verifiedUserOpt.orElseThrow(() -> new InvalidTokenRequestException("Phone Verification Token", phoneVerificationRequestDTO.getCode(),
		//		"Failed to confirm. Please generate a new phone verification request"));
		
		/*Optional<Users> verifiedUserOpt = null;
		verifiedUserOpt.orElseThrow(() -> new InvalidTokenRequestException("Phone Verification Token", phoneVerificationRequestDTO.getPhoneNumber(),
				"Failed to generate token. Please generate a new phone verification request"));*/
		
		Optional<PhoneVerificationToken> phoneToken = authService.phoneCodeToken(phoneVerificationRequestDTO);
		phoneToken.orElseThrow(() -> new PhoneCodeResetException(phoneVerificationRequestDTO.getPhoneNumber(),
				"Couldn't create a valid token"));
		PhoneVerificationToken phoneVerifyToken = phoneToken.get();
		
		tokenProvider.generateTokenFromUserPhone(phoneVerificationRequestDTO.getPhoneNumber());
		
		//UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromHttpUrl(clientUrl + clientResetPasswordPath);
		//OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken,
		//		urlBuilder);
		//applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
		
		return ResponseEntity.ok(new ApiResponse("Phone code: "+ phoneVerificationRequestDTO.getCode(), true));
	}
	/*public ResponseEntity<?> registerphonenumberconfirmation(@Valid @RequestBody PhoneCodeValidationRequestDTO phoneCodeValidationRequestDTO) {
		Optional<Users> verifiedUserOpt = authService.confirmPhoneRegistration(phoneCodeValidationRequestDTO.getPhoneNumber());
		verifiedUserOpt.orElseThrow(() -> new InvalidTokenRequestException("Phone Verification Token", phoneCodeValidationRequestDTO.getPhoneNumber(),
				"Failed to confirm. Please generate a new email verification request"));
		return ResponseEntity.ok(new ApiResponse("Phone number verified successfully", true));
	}*/

	@ApiOperation(value = "Reçois un code d'initialisation du mot de passe, réinitialise le mot de passe puis déclenche l'envoi" +
			"de mail de notifications de mise à jour du mot de passe.")
	@PostMapping("/password/reset")
	public ResponseEntity<?> resetPassword(@ApiParam(value = "The PasswordResetRequest dto") @Valid @RequestBody PasswordResetRequest passwordResetRequest) {
		Optional<Users> userOpt = authService.resetPassword(passwordResetRequest);
		userOpt.orElseThrow(() -> new PasswordResetException(passwordResetRequest.getToken(), "Error in resetting " +
				"password"));
		Users changedUser = userOpt.get();
		OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset Password",
				"Changed Successfully");
		applicationEventPublisher.publishEvent(onPasswordChangeEvent);
		return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
	}
	
	


	@ApiOperation(value = "Permet à un utilisateur de modifier son mot passe une fois connecté.")
	@PostMapping("/password/update")
	public ResponseEntity<?> updateUserPassword(@CurrentUser CustomUserDetails customUserDetails,
												@ApiParam(value = "The UpdatePasswordRequest dto") @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
		Users updatedUser = authService.updatePassword(customUserDetails, updatePasswordRequest)
				.orElseThrow(() -> new UpdatePasswordException("--Empty--", "No such user present."));

		OnUserAccountChangeEvent onUserPasswordChangeEvent =
				new OnUserAccountChangeEvent(updatedUser, "Update Password", "Change successful");
		applicationEventPublisher.publishEvent(onUserPasswordChangeEvent);

		return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
	}


	@ApiOperation(value = "Déconnecte l'utilisateur connecté en supprimant le refresh token associé à sa connexion.")
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@CurrentUser CustomUserDetails customUserDetails, long id) {
		userService.logoutUser(customUserDetails, id);
		return ResponseEntity.ok(new ApiResponse("Log out successful", true));
	}

	@ApiOperation(value = "Permet à l'utilisateur de vérifier son adresse mail à partir d'un code qui a été généré à cet effet.")
	@GetMapping("/registerconfirmationbyemail")
	public ResponseEntity<?> confirmRegistration(@ApiParam(value = "the token that was sent to the user email") @RequestParam("token") String token) throws URISyntaxException {
		Optional<Users> verifiedUserOpt = authService.confirmEmailRegistration(token);
		verifiedUserOpt.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token,
				"Failed to confirm. Please generate a new email verification request"));
		HttpHeaders httpHeaders = new HttpHeaders();
		URI txirideweb = new URI("https://dev.taxiride.biz/auth/message?token="+token);
		httpHeaders.setLocation(txirideweb);
		
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		//return ResponseEntity.ok(new ApiResponse("Email verified successfully", true));
	}
	
	/*public ResponseEntity<Object> redirectToExternalUrl() throws URISyntaxException {
	    URI yahoo = new URI("http://www..com");
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(yahoo);
	    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}*/

	/*@ApiOperation(value = "Permet à l'utilisateur de vérifier son mot de passe grâce à un code qui a été généré à cet effet.")
	@PostMapping("/registerconfirmationbyphone")
	public ResponseEntity<?> registerConfirmationByPhone(@Valid @RequestBody PhoneVerificationRequestDTO phoneVerificationRequestDTO) {
		
		Optional<Users> verifiedUserOpt = authService.confirmPhoneRegistration(phoneVerificationRequestDTO.getPhoneNumber());
		verifiedUserOpt.orElseThrow(() -> new InvalidTokenRequestException("Phone Verification Token", phoneVerificationRequestDTO.getPhoneNumber(),
				"Failed to confirm. Please generate a new email verification request"));
		return ResponseEntity.ok(new ApiResponse("Phone number verified successfully", true));
	}*/
	
	@ApiOperation(value = "Permet à l'utilisateur de vérifier son mot de passe grâce à un code qui a été généré à cet effet.")
	@PostMapping("/registerconfirmationbyphone")
	public ResponseEntity<ApiResponse> registerConfirmationByPhone(@Valid @RequestBody PhoneVerificationRequestDTO phoneVerificationRequestDTO) {
		
		String existeCode = userService.verifyIfCodeExist(phoneVerificationRequestDTO.getPhoneNumber());
        
        if(existeCode == null) {
        	return ResponseEntity.ok(new ApiResponse("Phone verified faid", false));
        }
        	
		
		userService.confirmPhoneRegistrations(phoneVerificationRequestDTO.getPhoneNumber(), phoneVerificationRequestDTO.getCode());
		
		//return userService.confirmPhoneRegistrations(phoneVerificationRequestDTO.getPhoneNumber(), phoneVerificationRequestDTO.getCode());
		
		return ResponseEntity.ok(new ApiResponse("Phone verified successfully", true));
	}
	
	

	

	@ApiOperation(value = "Regénération du token d'activation de vérification du mail.")
	@GetMapping("/resendRegistrationToken")
	public ResponseEntity<?> resendRegistrationToken(@ApiParam(value = "the initial token that was sent to the user " +
			" email after registration") @RequestParam("token") String existingToken) {
		Optional<EmailVerificationToken> newEmailTokenOpt = authService.recreateRegistrationToken(existingToken);
		newEmailTokenOpt.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
				"Users is already registered. No need to re-generate token"));

		Users registeredUser = newEmailTokenOpt.map(EmailVerificationToken::getUser)
				.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
						"No user associated with this request. Re-verification denied"));

		UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/auth" + "/registrationConfirmation");
		OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(
				registeredUser, urlBuilder, newEmailTokenOpt.get());
		applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);

		return ResponseEntity.ok(new ApiResponse("Email verification resent successfully", true));
	}


	@ApiOperation(value = "Regénération du token en se servant du token de rafraissement.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshJwtToken(@ApiParam(value = "The TokenRefreshRequest dto") @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
		Optional<String> updatedJwtToken = authService.refreshJwtToken(tokenRefreshRequest);
		updatedJwtToken.orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(),
				"Unexpected error during token refresh. Please logout and login again."));
		String refreshToken = tokenRefreshRequest.getRefreshToken();

		return ResponseEntity.ok(new JwtAuthenticationResponse(updatedJwtToken.get(), refreshToken,
				tokenProvider.getExpiryDuration()));
	}
        
        @ApiOperation(value = "Roles modification in the application.")
	@PostMapping("/updateRoles")
	public ResponseEntity<?> updateRole( @Valid @RequestBody RolesMofification rolesMofification) {
            try{
                Optional<Users> u=userService.findByEmailOrPhone(rolesMofification.getUsername());
                Optional<Roles> r= roleService.findByRole(rolesMofification.getRole());
                if(u.isPresent() && r.isPresent())
                    authService.modifyRoles(u.get().getId(), r.get().getId());
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("the modification worked", Boolean.TRUE));
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("An error occured",false));
            }
        }
}
