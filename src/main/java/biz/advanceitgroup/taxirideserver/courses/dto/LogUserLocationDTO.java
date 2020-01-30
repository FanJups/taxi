package biz.advanceitgroup.taxirideserver.courses.dto;

import javax.validation.constraints.NotNull;

import biz.advanceitgroup.taxirideserver.authentification.annotations.NullOrNotBlank;
import biz.advanceitgroup.taxirideserver.geolocalisation.entities.Country;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Log user location Request", description = "Log user  location request dto")
public class LogUserLocationDTO {
	@ApiModelProperty(value = "Valid Email or Phone number", allowableValues = "NonEmpty String")
	@NullOrNotBlank(message = "Email or Phone number can not be null")
	@NotNull(message = "Email or Phone number can not be null")
	private String emailOrPhoneNumber;

	@NotNull(message = "Country code can not be null")
	@ApiModelProperty(value = "Valid country code", required = true, allowableValues = "NonEmpty String")
	private String codePays;
	
	@NotNull(message = "City code can not be null")
	@ApiModelProperty(value = "Valid city code", required = true, allowableValues = "NonEmpty String")
    private String codeVille;
	
	@NotNull(message = "Longitude code can not be null")
	@ApiModelProperty(value = "Valid Longitude", required = true, allowableValues = "NonEmpty String")
    private double longitude;
	
	@NotNull(message = "lattitude code can not be null")
	@ApiModelProperty(value = "Valid lattitude", required = true, allowableValues = "NonEmpty String")
    private double latitude;
	
	@NotNull(message = "Ride id code can not be null")
	@ApiModelProperty(value = "Valid Ride id", required = true, allowableValues = "NonEmpty String")
	private Long RIDE_ID;
}
