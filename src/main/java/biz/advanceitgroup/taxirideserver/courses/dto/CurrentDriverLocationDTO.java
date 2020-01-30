package biz.advanceitgroup.taxirideserver.courses.dto;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.taxirideserver.geolocalisation.entities.Country;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Current driver location Request", description = "Current Driver location request dto")
public class CurrentDriverLocationDTO {
	
	@ApiModelProperty(value = "Valid Email or Phone number", allowableValues = "NonEmpty String")
	private String emailOrPhoneNumber;
	
	@Column(length = 1)
        private Boolean driverAvailable;
	
	private long timeoutDriverConnect;

	@ApiModelProperty(value = "Valid country code", required = true, allowableValues = "NonEmpty String")
	private String codePays;
	
	@ApiModelProperty(value = "Valid city code", required = true, allowableValues = "NonEmpty String")
    private String ville;
	
	@ApiModelProperty(value = "Valid Longitude", required = true, allowableValues = "NonEmpty String")
    private double longitude;
	
	@ApiModelProperty(value = "Valid lattitude", required = true, allowableValues = "NonEmpty String")
    private double latitude;
	
	
	
	private Long RIDE_ID;
        private String language;

    public CurrentDriverLocationDTO(String emailOrPhoneNumber, Boolean driverAvailable, long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
        this.driverAvailable = driverAvailable;
        this.timeoutDriverConnect = timeoutDriverConnect;
        this.codePays = codePays;
        this.ville = ville;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CurrentDriverLocationDTO(String emailOrPhoneNumber, Boolean driverAvailable, long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, Long RIDE_ID) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
        this.driverAvailable = driverAvailable;
        this.timeoutDriverConnect = timeoutDriverConnect;
        this.codePays = codePays;
        this.ville = ville;
        this.longitude = longitude;
        this.latitude = latitude;
        this.RIDE_ID = RIDE_ID;
    }
    
        
        
}
