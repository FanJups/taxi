package biz.advanceitgroup.taxirideserver.courses.entities;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente une note que l'on attribut soit au système, soit à un chauffeur ou à un passager sur la plateforme.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rating extends MainEntity {

    @Id
    @Column(name = "RATING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Information sur l'initiateur
    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users sender;

    private String senderFirstName;
    private String senderLastName;
    private String senderPhoneNumber;
    private Integer ratingTarget; // 0 = DRIVER, 1 = RIDER, 2 = APPLICATION
    private Double mark;
    private String comment;

    // Informations sur le destinataire
    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users receiver;

}
