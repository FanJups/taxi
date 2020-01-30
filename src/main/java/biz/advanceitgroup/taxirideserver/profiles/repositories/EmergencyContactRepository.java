package biz.advanceitgroup.taxirideserver.profiles.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.EmergencyContact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    // Rechercher tous les contacts d'un utilisateur
    List<EmergencyContact> findAllByUser(Users user);
    
    //vérifier si une numéro urgent n'existe pas encore
  	@Query(value ="SELECT * FROM emergency_contact WHERE contact_type=? and user_id=? and phone=? limit 1", nativeQuery = true)
  	List<EmergencyContact> verifierNumeroExiste(String contact_type, Long user_id, String phone);
  	

    // Rechercher tous les contacts d'un utilisateur
    List<EmergencyContact> findAllByUserAndContactType(Users user, Integer contactType);

}
