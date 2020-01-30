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
/*

helps to record the tracks of transport's states

*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LogRideEvent {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
	
	@NotNull(message = "Event type can not be null")
	private int eventType; 
	/**
	 		+ eventType = 0 => Date de départ du taxi
            + eventType = 1 => Date d'arrivée du taxi au point de ramassage
            + eventType = 2 => Date de départ du taxi du point de ramassage
            + eventType = 3 => Date d'arrivée du taxi au point de destination
	 */
	
	@NotNull(message = "Ride id code can not be null")
	private Long rideId;

    public LogRideEvent(Date eventDate, int eventType, Long rideId) {
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.rideId = rideId;
    }

}
