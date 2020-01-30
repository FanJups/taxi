package biz.advanceitgroup.taxirideserver.courses.entities;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import biz.advanceitgroup.taxirideserver.geolocalisation.entities.City;
import biz.advanceitgroup.taxirideserver.geolocalisation.entities.Country;
import biz.advanceitgroup.taxirideserver.profiles.entities.PaymentMode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


/**
 * Cette classe représente une course effectuée sur la plateforme TaxiRide.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trip extends MainEntity {

    @Id
    @Column(name = "TRIP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripID;

    // Rider's informations
    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users rider;

    private String riderFirstName;
    private String riderLastName;
    @Column(length = 1)
    private String riderGenderCode;

    @Column(length = 100)
    private String riderEmail;

    @Column(length = 30)
    private String riderPhone;

    @Temporal(TemporalType.DATE)
    private Date riderBirthDate;

    private String riderAddress;
    private long notificationDistance;

    @Column(length = 2)
    private String riderLangCode;

    private double riderLongitude;
    private double riderLatitude;
    
    //status
    private int tripStatus; //[0=WAITING; 1=CANCELED; 2=BIDDED; 3=STARTED; 4=ENDED]
    @Temporal(TemporalType.TIMESTAMP)
    private Date tripCancelDate;
    private int tripCancelRole; //[0=NONE; 1=RIDER; 2=DRIVER; 3=SYSTEM]


    // Driver's informations

    // Rider's informations
    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users driver;

    private String driverFirstName;
    private String driverLastName;
    private Character driverGenderCode;

    @Column(length = 100)
    private String driverEmail;

    @Column(length = 30)
    private String driverPhone;

    @Temporal(TemporalType.DATE)
    private Date driverBirthDate;

    private String driverAddress;
    private String driverProfession;

    @Column(length = 2)
    private String driverLangCode;
    private double driverStartLongitude;
    private double driverStartLattitude;
    private String driverStartAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date driverStartDate;

    private double pickupLongitude;
    private double pickupLatitude;
    private String pickupAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupArrivalDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupLeaveDate;

    private double arrivalLongitude;
    private double arrivalLatitude;
    private String arrivalAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDate;
    private double tripCost;
    private double tripCancelCost;
    private double estimatedTripDistance;
    private Long estimatedTripDuration;
    private String tripCostCalculFormula;
    //private String tripCancelCostCalculFormula;
    private String tripReservationCostCalculFormula;
    private int tripOptionDriverCancelTimeout;
    private int tripOptionRiderCancelTimeout;
    private String tripOptionDriverCancelFeeFormula;
    private String tripOptionRiderCancelFeeFormula;

    // Informations sur le mode de paiement
    @OneToOne(targetEntity = PaymentMode.class, fetch = FetchType.LAZY)
    private PaymentMode paymentMode;

    private String payementModeName;
    private String payementModeValue;
    private Integer paymentStatus; // 0 = PAID, 1 = PENDING, 2 = CANCELED, 3 = FAILED


    //L'option de course
    @OneToOne(targetEntity = TripOption.class)
    private TripOption tripOption;

    private String tripOptionCode;
    private String tripOptionName;
    private Double tripOptionBaseFare;
    private Double tripOptionMinuteRate;
    private Double tripOptionKilometerRate;
    private Long tripOptionEstimatedWaitingTime;
    private String tripOptionFeeFormula;
    private String tripOptionCancelFeeFormula;
    private String tripOptionReservationFeeFormula;
    private Double tripOptionCommission;


    // Informations sur le pays
    @OneToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    private Country country;


    // Informations sur la ville
    @OneToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    private City city;
    private String cityCode;
    private Time trustContactNotificationTimeMin;
    private Time trustContactNotificationTimeMax;
	public void setCountry(String codePays) {
		
	}

}
