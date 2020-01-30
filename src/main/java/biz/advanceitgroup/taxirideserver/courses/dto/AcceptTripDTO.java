package biz.advanceitgroup.taxirideserver.courses.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Accept trip Request", description = "Accept trip request dto")
public class AcceptTripDTO {
	
	String emailorphone;
	
	long tripID;
	
	double longitude;
	
	double latitude;
	double durationBeforePickUp;
        double distanceBeforePickup;
	long requestid;
        
}
