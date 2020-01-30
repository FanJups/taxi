package biz.advanceitgroup.taxirideserver.courses.dto;


import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Save Rider notation: TaxiRide.
 * @author Saha Merlin
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Rider notation Request", description = "Rider notation  request dto")
public class RiderNotationDTO {
	 
	@NotNull(message = "Driver id can not be null")
	private long riderID; 
	@NotNull(message = "Note can not be null")
	private double note; 
	@NotNull(message = "Commentaire can not be null")
	private String commentaire; 
	@NotNull(message = "Enregistration date can not be null")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnreg; 
	@NotNull(message = "Ride id can not be null")
	private long rideID;
	@NotNull(message = "Rider id can not be null")
	private long driverID;

}
