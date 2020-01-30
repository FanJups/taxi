package biz.advanceitgroup.taxirideserver.courses.entities;

import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente une option de course que l'on définit sur la plateforme TaxiRide.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TripOption extends MainEntity {

    @Id
    @Column(name = "TRIP_OPTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double driverCancelTimeout;
    private double riderCancelTimeout;
    
    private String optionCode;
    private String optionName;
    private Double baseFare;
    private Double minuteRate;
    private Double kilometerRate;
    private String optionDescription;
    private Long estimatedWaitingTime;
    private String tripFeeFormula;
    //private String cancelFeeFormula;
    private String driverCancelFeeFormula;
    private String riderCancelFeeFormula;
    private String reservationFeeFormula;
    private Double tripCommission;
    
    private String estimatedTripDistance;
    private String tripCostCalculFormula;
    //private String tripCancelCostCalculFormula;
    private String tripReservationCostCalculFormula;

    @Column(length = 100)
    private String mapCabImageMimeType;
    private String mapCabImageUrl;

   /* @OneToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    private Country country;*/
    
    private String countryCode;
    
    private String cityCode;
    private Time time ;
    private Time tripOptionTrustContactNotificationTimeMax  ;
    
}
