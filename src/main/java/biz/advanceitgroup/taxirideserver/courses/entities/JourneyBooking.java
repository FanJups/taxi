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
 * Cette classe représente la réservation d'un voyage sur la plateforme TaxiRide.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JourneyBooking extends MainEntity {


    @Id
    @Column(name = "JOURNEY_BOOKING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Informations sur le passager
    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users rider;

    private String riderFirstName;
    private String riderLastName;
    private String riderPhoneNumber;
    private String riderReferealCode;

    @Column(length = 100)
    private String startCity;

    @Column(length = 100)
    private String endCity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    private Double cost;

    private Integer numberSeat;

    private Integer journeyBookingType; // 0 = BOOKING, 1 = INVITATION

}
