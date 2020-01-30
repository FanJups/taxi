package biz.advanceitgroup.taxirideserver.profiles.repositories;

import biz.advanceitgroup.taxirideserver.profiles.entities.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode, Long> {

    // Vérifie si le mode de paiement existe suivant le type de paiement
    Boolean existsByPaymentType(Integer type);

    // Rechercher suivant le type de payment
    Optional<PaymentMode> findByPaymentType(Integer type);

    // Récupérer toutes les méthodes de payment d'un type donné
    List<PaymentMode> findAllByPaymentType(Integer type);
    
  
  	

}
