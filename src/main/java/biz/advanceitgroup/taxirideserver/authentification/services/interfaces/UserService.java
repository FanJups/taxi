package biz.advanceitgroup.taxirideserver.authentification.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.securities.oauth2.user.CustomUserDetails;
import biz.advanceitgroup.taxirideserver.profiles.entities.EmergencyContact;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.dto.RegistrationRequest;
import biz.advanceitgroup.taxirideserver.authentification.dto.socialRegistrationRequest;
import biz.advanceitgroup.taxirideserver.authentification.entities.RefreshToken;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

public interface UserService {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByPhoneNumber(String phone);
    
    Optional<Users>  findByUsername(String Username);
    
    Optional<Users> findByEmailOrPhone(String emailOrPhone);

    Users getLoggedInUser(String email);

    Optional<Users> findById(Long Id);

    Users findByFirstName(String firstName);
    
    int confirmPhoneRegistrations(String phone, String code);

    Users save(Users user);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phone);

    Boolean existsByReferalCodeAndPhone(String referalCode, String phone);

    Users createUser(RegistrationRequest registerRequest);
    
    Users createNewSocialUser(socialRegistrationRequest newRegistrationRequest);

    void verifyRefreshAvailability(RefreshToken refreshToken);
    Optional<Users> findByRefreshToken(RefreshToken refreshToken) ;

    Optional<RefreshToken> findRefreshTokenById(Long id);

    void logoutUser(CustomUserDetails customUserDetails, Long id) ;

    Boolean hasDocument(Users user, Integer number);
    
    String verifyIfCodeExist(String phone);
    
    void addPushNotificationsToken(String token,long id);

}
