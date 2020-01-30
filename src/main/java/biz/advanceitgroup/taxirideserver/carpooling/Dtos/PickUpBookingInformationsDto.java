package biz.advanceitgroup.taxirideserver.carpooling.Dtos;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
*
* @author Fanon Jupkwo 
* 
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickUpBookingInformationsDto {
	
	 
    private Date departureDate;
    private String pickupLocation ;
    private String depositLocation;
    private long travelId;
    private String emailOrPhone;
    private String departureCity;
    private String arrivalCity;
    private String language;
    

}
