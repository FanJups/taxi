package biz.advanceitgroup.taxirideserver.profiles.entities;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente un mode de payment qu'un utilisateur enregistre dans son profil, il peut être ou non
 * le moyen de paiement par défaut de l'utilisateur concerné.
 * @author Simon Ngang
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserPaymentMode extends MainEntity {

    @Id
    @Column(name = "USER_PAYMENT_MODE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value; // La valeur du mode de payment (numéro mobile money, numéro du compte bancaire, etc)

    private Boolean isDefault; // Ce mode de paiement est-il celui utilisé par défaut par l'utilisateur ?

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private Users user; // L'utilisateur possédant ce mode de paiement

    @OneToOne(targetEntity = PaymentMode.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PAYMENT_MODE_ID")
    private PaymentMode paymentMode; // Le mode de payment adopté
    
    private int paymentModeType;

    public UserPaymentMode(Users user, PaymentMode paymentMode, String value, Boolean isDefault, int paymentModeType) {
        this.user = user;
        this.paymentMode = paymentMode;
        this.value = value;
        this.isDefault = isDefault;
        this.paymentModeType = paymentModeType;
    }
    
}
