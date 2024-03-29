package biz.advanceitgroup.taxirideserver.authentification.services.impl;

import biz.advanceitgroup.taxirideserver.authentification.enums.AuthProvider;
import biz.advanceitgroup.taxirideserver.authentification.securities.oauth2.user.CustomUserDetails;
import biz.advanceitgroup.taxirideserver.authentification.entities.Roles;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.RefreshTokenService;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.RoleService;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.UserService;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.TokenRefreshException;
import biz.advanceitgroup.taxirideserver.authentification.dto.RegistrationRequest;
import biz.advanceitgroup.taxirideserver.authentification.dto.socialRegistrationRequest;
import biz.advanceitgroup.taxirideserver.authentification.entities.RefreshToken;

import biz.advanceitgroup.taxirideserver.authentification.repositories.RoleRepository;
import biz.advanceitgroup.taxirideserver.authentification.repositories.UserRepository;
import biz.advanceitgroup.taxirideserver.commons.helpers.TaxiRideCommonServices;
import biz.advanceitgroup.taxirideserver.profiles.repositories.OfficialDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.validation.Valid;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private TaxiRideCommonServices taxiRideCommonServices;

    @Autowired
    private OfficialDocumentRepository officialDocumentRepository;

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> findByPhoneNumber(String phone) {
        return userRepository.findByPhoneNumber(phone);
    }

    public Users getLoggedInUser(String email) {
        return findByEmail(email).get();
    }

    public Optional<Users> findById(Long Id) {
        return userRepository.findById(Id);
    }

    public Users findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }
    
    public Optional<Users>  findByUsername(String Username){
        return userRepository.findByUsername(Username, Username);
    }
    
    public Optional<Users> findByEmailOrPhone(String emailOrPhone){
         return userRepository.findByEmailOrPhone(emailOrPhone);
	
    }

    public Users save(Users user) {
        return userRepository.save(user);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existsByReferalCodeAndPhone(String referalCode, String phone) {
        return userRepository.existsByReferalCodeAndPhoneNumber(referalCode, phone);
    }

    // Création d'un utilisateur dans le système
    public Users createUser(RegistrationRequest registerRequest) {
        Users newUser = new Users();
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setProvider(AuthProvider.local);
        newUser.setRefreshActive(true);
        newUser.setActive(true);
        newUser.setIsEmailVerified(false);
        newUser.setIsVerified(false);
        newUser.setIsPhoneVerified(false);
        newUser.setVerificationCode(registerRequest.getVerificationCode());
        newUser.setContryCode(registerRequest.getContryCode());
        

        // Attribuer un rôle à l'utilisateur (RIDER par défaut)
        Optional<Roles> role = roleService.findByRole("ROLE_RIDER");
        if(role.isPresent()){
            newUser.addRole(role.get());
        }

        // Générer un code de référence et attribuer à l'utilisateur (referalCode)
        newUser.setReferalCode(taxiRideCommonServices.generateRandomDigitsLettersCode(6));
        newUser.setReferalCodeUsed(false);

        return newUser;
    }
    
   

    public void verifyRefreshAvailability(RefreshToken refreshToken) {
        Optional<Users> userOpt = findByRefreshToken(refreshToken);
        userOpt.orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(),
                "No device found for the matching token. Please login again"));

        Optional<Boolean> userRefreshEnabledOpt = userOpt.map(Users::getRefreshActive);
        userRefreshEnabledOpt.orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(),
                "Refresh blocked for the device. Please login through a different device"));
    }

    public Optional<Users> findByRefreshToken(RefreshToken refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    public Optional<RefreshToken> findRefreshTokenById(Long id) {
        return userRepository.findRefreshTokenById(id);
    }

    public Users ajouter(Users user) {
        return userRepository.save(user);
    }


    /**
     *Déconnectez l'utilisateur donné et supprimez le jeton d'actualisation qui lui est associé. Si aucun appareil
     * id est trouvé correspondant à la base de données pour l'utilisateur donné, lève une exception de déconnexion.
     */

    public void logoutUser(CustomUserDetails customUserDetails, Long id) {

        Optional<Users> userOpt = userRepository.findById(id);

        userOpt.map(Users::getRefreshToken)
                .map(RefreshToken::getId)
                .ifPresent(refreshTokenService::deleteById);
    }

    @Override
    public Boolean hasDocument(Users user, Integer number) {
        return officialDocumentRepository.existsByUserAndNumber(user, number);
    }

    
  //créer un utilisateur par les réseaux sociaux
    public Users createNewSocialUser(socialRegistrationRequest registerRequest) {
      	Users newUser = new Users();
          newUser.setPhoneNumber(registerRequest.getPhoneNumber());
          newUser.setEmail(registerRequest.getEmail());
          newUser.setPassword("");
          newUser.setProvider(AuthProvider.facebook);
          newUser.setRefreshActive(true);
          newUser.setActive(true);
          newUser.setIsEmailVerified(false);
          newUser.setIsVerified(false);
          newUser.setIsPhoneVerified(false);
          newUser.setVerificationCode(registerRequest.getVerificationCode());
          newUser.setContryCode(registerRequest.getContryCode());
       // Attribuer un rôle à l'utilisateur (RIDER par défaut)
          Optional<Roles> role = roleService.findByRole("ROLE_RIDER");
          if(role.isPresent()){
              newUser.addRole(role.get());
          }
  		
       // Générer un code de référence et attribuer à l'utilisateur (referalCode)
          newUser.setReferalCode(taxiRideCommonServices.generateRandomDigitsLettersCode(6));
          newUser.setReferalCodeUsed(false);

          return newUser;
  	}

    
    //Vérification du numéro de téléphone
    @Override
	public int confirmPhoneRegistrations(String phone, String code) {
			userRepository.confirmPhoneRegistrations(phone, code);
		return 1;
	}

	@Override
	public String verifyIfCodeExist(String phone) {
		
		return userRepository.verifyIfCodeExist(phone);
	}

    @Override
    public void addPushNotificationsToken(String token, long id) {
               userRepository.addPushNotificationsToken(token, id);
    }

  
	

	

	

}
