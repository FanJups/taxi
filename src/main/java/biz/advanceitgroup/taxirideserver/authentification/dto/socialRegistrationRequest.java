package biz.advanceitgroup.taxirideserver.authentification.dto;

import biz.advanceitgroup.taxirideserver.authentification.annotations.ValidEmail;
import biz.advanceitgroup.taxirideserver.authentification.enums.AuthProvider;

import javax.validation.constraints.NotEmpty;

import biz.advanceitgroup.taxirideserver.authentification.annotations.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Registration Request", description = "The registration request dto")
public class socialRegistrationRequest {

	@NullOrNotBlank(message = "Registration phone number can be null but not blank")
	@ApiModelProperty(value = "Add valid phone number", allowableValues = "NonEmpty String")
	private String phoneNumber;

	@ValidEmail
	@ApiModelProperty(value = "Add valid email", required = true, allowableValues = "NonEmpty String")
	private String email;
	
	//@NotBlank(message = "Verification Code can not be empty")
	private String verificationCode;
	
	@NotEmpty(message = "Country code can be null but not blank")
	private String contryCode;

	private AuthProvider provider ;

}
