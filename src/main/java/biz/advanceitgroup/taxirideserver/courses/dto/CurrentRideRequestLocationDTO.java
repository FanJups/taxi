package biz.advanceitgroup.taxirideserver.courses.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Current Ride Request Location", description = "Current Ride request dto")

public class CurrentRideRequestLocationDTO {

	@NotNull(message = "Ride id can not be null")
	private long rideID; 
	
	@NotNull(message = "Country can not be null")
	@Column(length = 3)
	private String codePays; 
	
	private String emailorphone;
	
	@NotNull(message = "City code can not be null")
	@Column(length = 15)
	private String codeVille; 
	
	@NotNull(message = "Longitude can not be null")
	private double longitude; 
	
	@NotNull(message = "Latitude can not be null")
	private double latitude; 
	
	private long driverID;
	
	private long rideDurationBeforePickUp;
}
