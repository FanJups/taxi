package biz.advanceitgroup.taxirideserver.profiles.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.UserPaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
public interface UserPaymentModeRepository extends JpaRepository<UserPaymentMode, Long> {
    // Rechercher tous les modes de paiement d'un utilisateur
    List<UserPaymentMode> findAllByUser(Users user);
    
    //Set default payment methode 
  	@Modifying(clearAutomatically = true)
  	@Transactional
  	@Query(value ="UPDATE user_payment_mode SET is_default=1 WHERE user_id=? AND payment_mode_type=? ", nativeQuery = true)
  	int defaultPayment(Long id, int paymentnumber);
  	
  	//initialisation de l'ancien mode de paiement par defaut
  	@Modifying(clearAutomatically = true)
  	@Transactional
  	@Query(value ="UPDATE user_payment_mode SET is_default=0 WHERE user_id=? ", nativeQuery = true)
  	int initdefaultPayment(Long id);
  	
  	//delete default payment methode 
  	@Modifying(clearAutomatically = true)
  	@Transactional
  	@Query(value ="DELETE FROM user_payment_mode WHERE user_id=? AND payment_mode_type=? ", nativeQuery = true)
	int deletePayement(Long id, int paymentnumber);
  	
  	//fin default payment methode 
  	@Query(value ="SELECT * FROM user_payment_mode WHERE user_id=? AND is_default=1 LIMIT 1", nativeQuery = true)
	Optional<UserPaymentMode> findDefaultPayment(Long id);

}
