package biz.advanceitgroup.taxirideserver.profiles.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.PromotionalCode;
import biz.advanceitgroup.taxirideserver.profiles.entities.UserPromotionalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPromotionalCodeRepository extends JpaRepository<UserPromotionalCode, Long> {

    // Rechercher tous les codes promotionnels d'un utilisateur
    List<UserPromotionalCode> findAllByUser(Users user);

    // Vérifie si un code promotionnel existe par utilisateur et par clé
    Boolean existsByUserAndPromotionalCode(Users user, PromotionalCode promotionalCode);

    // Vérifie si un code promotionnel de l'utilisateur existe
    Boolean existsByPromotionalCode(PromotionalCode code);

}
