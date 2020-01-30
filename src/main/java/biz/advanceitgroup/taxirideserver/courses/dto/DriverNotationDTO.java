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
 * Save Driver notation: TaxiRide.
 * @author Saha Merlin
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Driver notation Request", description = "Driver notation  request dto")
public class DriverNotationDTO {
	 
	@NotNull(message = "Driver id can not be null")
	private long driverID; 
	@NotNull(message = "Note can not be null")
	private double note; 
	@NotNull(message = "Commentaire can not be null")
	private String commentaire;
	@NotNull(message = "Ride id can not be null")
	@NotNull(message = "Enregistration date can not be null")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnreg;
	private long rideID;
	@NotNull(message = "Rideer id can not be null")
	private long riderID;

}
