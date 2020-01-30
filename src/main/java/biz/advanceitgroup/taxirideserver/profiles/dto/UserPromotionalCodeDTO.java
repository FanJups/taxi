package biz.advanceitgroup.taxirideserver.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPromotionalCodeDTO {

    private Long id;
    private String phoneNumber; // L'utilisateur qui enregistre le code promotionnel sur son profil
    private Long userId; // L'identifiant du propriétaire
    private String codeKey; // La code promotionnel associé
    private Boolean promotionalCodeUsed; // Vérifie si le code promotionnel est déjà utilisé ou pas
    private String language;

    public UserPromotionalCodeDTO(Long id, String phoneNumber, Long userId, String codeKey, Boolean promotionalCodeUsed) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.codeKey = codeKey;
        this.promotionalCodeUsed = promotionalCodeUsed;
    }

   

   
    
}
