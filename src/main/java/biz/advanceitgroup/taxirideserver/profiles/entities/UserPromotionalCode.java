package biz.advanceitgroup.taxirideserver.profiles.entities;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente un code promotionnel qu'un utilisateur a enregistré dans son profil sur la plateforme TaxiRide.
 * Ce code promotionnel peut avoir été utilisé ou non par le propriétaire (celui qui l'enregistre).
 * @author Simon Ngang
 *
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserPromotionalCode extends MainEntity {

    @Id
    @Column(name = "USER_PROMOTIONAL_CODE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = PromotionalCode.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PROMOTIONAL_CODE_ID")
    private PromotionalCode promotionalCode; // Le code promotionnel associé

    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private Users user; // L'utilisateur ayant enregistré ce code promotionnel

    private Boolean promotionalCodeUsed; // Vérifie si le code promotionnel est déjà utilisé ou pas

    // Méthode permettant de vérifier que le code promotionnel de l'utilisateur est encore valide
    public Boolean isValid(){
        return (!this.getPromotionalCodeUsed()) && (!this.getPromotionalCode().hasExpired());
    }

}
