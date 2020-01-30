package biz.advanceitgroup.taxirideserver.courses.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Send notification Request", description = "Send notification request dto")
public class NotificationsDTO {

	@NotNull(message = "Phone number can not be null")
	@ApiModelProperty(value = "Valid Ride id", required = true, allowableValues = "NonEmpty String")
	private String phoneNumber;
	
	@NotNull(message = "message can not be null")
	@ApiModelProperty(value = "message", required = true, allowableValues = "NonEmpty String")
    private String message;
	
	@NotNull(message = "notification type can not be null")
	@ApiModelProperty(value = "Valid notification type", required = true, allowableValues = "NonEmpty int")
    private int notifType; 
	/**
	 * + notifType = 0 => Push notification
       + notifType = 1 => email + push notification
       + notifType = 2 => SMS + push notification
       + notifType = 3 => email + SMS + Push notification
	 */
	
	
	
	
}
