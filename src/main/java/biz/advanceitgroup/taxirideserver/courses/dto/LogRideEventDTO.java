package biz.advanceitgroup.taxirideserver.courses.dto;


import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Ride event Request", description = "Log ride event request dto")
public class LogRideEventDTO {
	
	@NotNull(message = "Event type can not be null")
	private int eventType; 
	/**
            + eventType = 0 => Date de départ du taxi
            + eventType = 1 => Date d'arrivée du taxi au point de ramassage
            + eventType = 2 => Date de départ du taxi du point de ramassage
            + eventType = 3 => Date d'arrivée du taxi au point de destination
	 */
	@NotNull(message = "Ride id can not be null")
	private Long RIDE_ID;
        private String language;

    public LogRideEventDTO(int eventType, Long RIDE_ID) {
        this.eventType = eventType;
        this.RIDE_ID = RIDE_ID;
    }
        
}
