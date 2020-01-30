package biz.advanceitgroup.taxirideserver.courses.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Save Rider notation: TaxiRide.
 * @author Saha Merlin
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RiderNotation {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	@NotNull(message = "rider id can not be null")
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
	@NotNull(message = "Driver id can not be null")
	private long driverID;

    public RiderNotation(long riderID, double note, String commentaire, Date dateEnreg, long rideID, long driverID) {
        this.riderID = riderID;
        this.note = note;
        this.commentaire = commentaire;
        this.dateEnreg = dateEnreg;
        this.rideID = rideID;
        this.driverID = driverID;
    }

}
