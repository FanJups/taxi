package biz.advanceitgroup.taxirideserver.courses.dto;



import java.util.Date;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Post Ride Request", description = "Post Ride request dto")
public class RidesDTO {
	
	@NotNull(message = "Phone number can not be null")
	private String phoneNumber;
	
	@NotNull(message = "Start point longititude can not be null")
	private double startPointLongitude;
	
	@NotNull(message = "Start point latitude can not be null")
	private double startPointLatitude;
	
	@NotNull(message = "Departure diparture can not be null")
	private String departurePoint;
	
	@NotNull(message = "End point longititude can not be null")
	private double endPointLongitude;
	
	@NotNull(message = "End point latitude can not be null")
	private double endPointLatitude;
	
	@NotNull(message = "Arrival point can not be null")
	private String arrivalPoint;
	
	private String codeOption;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date postDate;
	
	@NotNull(message = "Country code can not be null")
	private String codePays;
	
	@NotNull(message = "City code can not be null")
	private String codeVille;
	
	private Long availableCashAmount;
	
	private int seatNumber;
	
	private Long estimatedDuration;
	
	private double estimatedTripDistance;
	
	private Long estimatedTripLength;
	
	private Double tripCost;
	
	private Long tripTimeOut;
	
	private Long rayon;
        
        private double riderLongitude;
        private double riderLattitude;
        private String language;


    public RidesDTO(String phoneNumber, double startPointLongitude, double startPointLatitude, String departurePoint, double endPointLongitude, double endPointLatitude, String arrivalPoint, String codePays, String codeVille, Long estimatedDuration, double estimatedTripDistance, Long estimatedTripLength,String language) {
        this.phoneNumber = phoneNumber;
        this.startPointLongitude = startPointLongitude;
        this.startPointLatitude = startPointLatitude;
        this.departurePoint = departurePoint;
        this.endPointLongitude = endPointLongitude;
        this.endPointLatitude = endPointLatitude;
        this.arrivalPoint = arrivalPoint;
        this.codePays = codePays;
        this.codeVille = codeVille;
        this.estimatedDuration = estimatedDuration;
        this.estimatedTripDistance = estimatedTripDistance;
        this.estimatedTripLength = estimatedTripLength;
        this.language=language;
    }

    public RidesDTO(String phoneNumber, double startPointLongitude, double startPointLatitude, String departurePoint, double endPointLongitude, double endPointLatitude, String arrivalPoint, String codePays, String codeVille, Long estimatedDuration, double estimatedTripDistance, Long estimatedTripLength, double riderLongitude, double riderLattitude) {
        this.phoneNumber = phoneNumber;
        this.startPointLongitude = startPointLongitude;
        this.startPointLatitude = startPointLatitude;
        this.departurePoint = departurePoint;
        this.endPointLongitude = endPointLongitude;
        this.endPointLatitude = endPointLatitude;
        this.arrivalPoint = arrivalPoint;
        this.codePays = codePays;
        this.codeVille = codeVille;
        this.estimatedDuration = estimatedDuration;
        this.estimatedTripDistance = estimatedTripDistance;
        this.estimatedTripLength = estimatedTripLength;
        this.riderLongitude = riderLongitude;
        this.riderLattitude = riderLattitude;
    }

    
	
	
}
