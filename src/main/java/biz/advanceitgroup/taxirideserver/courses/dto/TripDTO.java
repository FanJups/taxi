package biz.advanceitgroup.taxirideserver.courses.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.courses.entities.TripOption;
import biz.advanceitgroup.taxirideserver.geolocalisation.entities.City;
import biz.advanceitgroup.taxirideserver.geolocalisation.entities.Country;
import biz.advanceitgroup.taxirideserver.profiles.entities.PaymentMode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Trip Request", description = "Trip request dto")

public class TripDTO {
	
	// Rider
	@NotNull(message = "Rider can not be null")
	@ApiModelProperty(value = "Valid user", required = true, allowableValues = "NonEmpty User")
    private Users rider;
	@NotNull(message = "Rider first name can not be null")
	@ApiModelProperty(value = "Rider first name", required = false, allowableValues = "Empty String")
    private String riderFirstName;
	@NotNull(message = "Rider last name can not be null")
	@ApiModelProperty(value = "Rider last name", required = false, allowableValues = "Empty String")
    private String riderLastName;
	@NotNull(message = "Rider gender name can not be null")
	@ApiModelProperty(value = "Rider gender code", required = false, allowableValues = "Empty Char")
    private Character riderGenderCode;
	@NotNull(message = "Rider email can not be null")
	@ApiModelProperty(value = "Rider email", required = false, allowableValues = "Empty String")
    private String riderEmail;
	@NotNull(message = "Rider phone can not be null")
	@ApiModelProperty(value = "Rider phone", required = false, allowableValues = "Empty String")
    private String riderPhone;
    @Temporal(TemporalType.DATE)
    private Date riderBirthDate;
    @NotNull(message = "Rider adress can not be null")
	@ApiModelProperty(value = "Rider adress", required = false, allowableValues = "Empty String")
    private String riderAddress;
    private String riderLangCode;
    private double riderLongitude;
    private double riderLatitude;
    
    private Double notificationDistance;
  
    //Trip
    private Double tripCost;
    private Double estimatedTripDistance;
    private Long estimatedTripDuration;
    private String tripCostCalculFormula;
    private String tripCancelCostCalculFormula;
    private String tripReservationCostCalculFormula;

    // Payment mode
    //@OneToOne(targetEntity = PaymentMode.class, fetch = FetchType.LAZY)
    private String paymentMode;

    private String payementModeName;
    private String payementModeValue;
    private Integer paymentStatus; // 0 = PAID, 1 = PENDING, 2 = CANCELED, 3 = FAILED


    //Trip option
    //@OneToOne(targetEntity = TripOption.class)
    private String tripOption;

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


    private String country;
    
    private String city;

}
