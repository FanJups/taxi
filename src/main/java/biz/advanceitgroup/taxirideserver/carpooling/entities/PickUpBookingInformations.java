package biz.advanceitgroup.taxirideserver.carpooling.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
*
* @author Fanon Jupkwo
*/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PickUpBookingInformations {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date departureDate;
    private String pickupLocation ;
    private String depositLocation;
    private long travelId;
    private String emailOrPhone;
    @Column(length = 50)
    private String departureCity;
    @Column(length = 50)
    private String arrivalCity;
    
    
    
	public PickUpBookingInformations(Date departureDate, String pickupLocation, String depositLocation, long travelId,
			String emailOrPhone,String departureCity,String arrivalCity) {
		this.departureDate = departureDate;
		this.pickupLocation = pickupLocation;
		this.depositLocation = depositLocation;
		this.travelId = travelId;
		this.emailOrPhone = emailOrPhone;
		this.departureCity=departureCity;
		this.arrivalCity=arrivalCity;
	}
    
    
	

}
