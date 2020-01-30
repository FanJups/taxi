package biz.advanceitgroup.taxirideserver.profiles.repositories;

import biz.advanceitgroup.taxirideserver.profiles.entities.PromotionalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionalCodeRepository extends JpaRepository<PromotionalCode, Long> {

   // Vérifier l'existence d'un code promotional à partir de la clé du code
   Boolean existsByCodeKey(String key);

   // Rechercher le code promotionnel suivant la clé
   Optional<PromotionalCode> findByCodeKey(String key);
}
