package biz.advanceitgroup.taxirideserver.authentification.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.EmergencyContact;
import biz.advanceitgroup.taxirideserver.authentification.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	@Override
	Optional<Users> findById(Long id);

	Optional<Users> findFirstByOrderByName();

	Users findByFirstName(String firstName);

	Boolean existsByEmail(String email);

	Boolean existsByPhoneNumber(String phoneNumber);

	Boolean existsByReferalCodeAndPhoneNumber(String referalCode, String phoneNumber);

	Optional<Users> findByEmail(String email);

	Optional<Users> findByPhoneNumber(String phone);

	Optional<RefreshToken> findRefreshTokenById(Long id);

	Optional<Users> findByRefreshToken(RefreshToken refreshToken);

	// Méthode permettant de retrouver un utilisateur suivant son adresse numéro de téléphone ou son addresse mail
	@Query("SELECT user FROM Users user WHERE user.phoneNumber =:phone OR user.email =:email")
	Optional<Users> findByUsername(@Param("phone") String phone, @Param("email") String email);
        
        @Query(value="SELECT * from users WHERE email=?1 OR phone_number=?1", nativeQuery = true)
        Optional<Users> findByEmailOrPhone(String emailOrPhone);
	
	//Validation du numéro de téléphone
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update Users u set u.isPhoneVerified =true where u.phoneNumber =:x and u.verificationCode=:y")
	int confirmPhoneRegistrations(@Param("x") String phone_number, @Param("y") String verification_code);
	
	
	
	//Verify if code exist
  	@Query(value ="SELECT verification_code FROM users WHERE phone_number=? limit 1", nativeQuery = true)
  	String verifyIfCodeExist(String phone_number);
  	
        @Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update Users set token_fcm=?1 where user_id=?2", nativeQuery = true)
	void addPushNotificationsToken(String token,long id);
        
}
