package biz.advanceitgroup.taxirideserver.courses.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Save current ride request location: TaxiRide.
 * @author Saha Merlin
 *
 */

@Data
@NoArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CurrentRideRequestLocation {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long rideID; 
	
	@NotNull(message = "Country can not be null")
	@Column(length = 6)
	private String codePays; 

	private String emailorphone;
	
	@NotNull(message = "City code can not be null")
	@Column(length = 15)
	private String ville;
	
	
	@NotNull(message = "Longitude can not be null")
	private double longitude; 
	
	@NotNull(message = "Latitude can not be null")
	private double latitude; 
	
	/*@NotNull(message = "Enregistration date can not be null")
	@Temporal(TemporalType.TIMESTAMP)*/
	private Date dateEnreg;
	
	private long driverID;
	
	private long rideDurationBeforePickUp;
	
	private String tripOptionID;
	
	private long tripId;
	
	private long rayon;
        
        private Boolean isInTheRange;
        
        private double distance;

    public CurrentRideRequestLocation(String codePays, String emailorphone, String ville, double longitude, double latitude, Date dateEnreg, long driverID, long rideDurationBeforePickUp, String tripOptionID, long tripId, long rayon, Boolean isInTheRange, double distance) {
        this.codePays = codePays;
        this.emailorphone = emailorphone;
        this.ville = ville;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateEnreg = dateEnreg;
        this.driverID = driverID;
        this.rideDurationBeforePickUp = rideDurationBeforePickUp;
        this.tripOptionID = tripOptionID;
        this.tripId = tripId;
        this.rayon = rayon;
        this.distance = distance;
        this.isInTheRange =isInTheRange;
    }
        
      
}
