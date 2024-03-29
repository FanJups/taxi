package biz.advanceitgroup.taxirideserver.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentModeDTO {

    private Long id;
    private String phoneNumber; // Le propriétaire du mode de paiement
    private Long userId; // L'identifiant du propriétaire
    private Long paymentModeId; // L'identifiant du mode de payment
    private Integer paymentType; // Le type du mode de payment
    private String value; // La valeur du mode de payment (numéro mobile money, numéro du compte bancaire, etc)
    private Boolean isDefault; // Ce mode de paiement est-il celui utilisé par défaut par l'utilisateur ?
    private String language;

    public UserPaymentModeDTO(Long id, String phoneNumber, Long userId, Long paymentModeId, Integer paymentType, String value, Boolean isDefault) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.paymentModeId = paymentModeId;
        this.paymentType = paymentType;
        this.value = value;
        this.isDefault = isDefault;
    }

    
    
}
