package biz.advanceitgroup.taxirideserver.profiles.entities;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente un contact d'urgence (0) ou de confiance (1) qu'un utilisateur enregistre
 * dans son profil sur la plateforme TaxiRide.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmergencyContact extends MainEntity {

    @Id
    @Column(name = "EMERGENCY_CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nom du contact

    @Column(length = 30)
    private String phone; // Le numéro de téléphone du contact

    private Integer contactType; // 0 = Emergency, 1= Confidence

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private Users user; // L'utilisateur possédant ce contact

}
