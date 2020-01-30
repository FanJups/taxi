package biz.advanceitgroup.taxirideserver.courses.entities;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Cette classe représente un voyage sur la plateforme TaxiRide.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Journey extends MainEntity {

    @Id
    @Column(name = "JOURNEY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Informations sur le chauffeur
    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users driver;

    private String driverPhoneNumber;
    private String driverFirstName;
    private String driverLastName;

    private String journeyName;

    @Column(length = 100)
    private String startCity;

    @Column(length = 100)
    private String arrivalCity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    private Integer seatsAvailable;

    private Double maximumWaitingTime;
    private Double journeyCommission;

    @Column(length = 500)
    private String conditions;

}
