package biz.advanceitgroup.taxirideserver.notifications.fcm.dto;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {
	 @NotBlank
	 private String phoneNumber; // to
	 
	 //@NotBlank
	 //private String fromphoneNumber; // from

	 @NotBlank
	 private String message;
	 
	 /*public Sms(@JsonProperty("tophoneNumber") String tophoneNumber,
			 @JsonProperty("fromphoneNumber") String fromphoneNumber,
             @JsonProperty("message") String message) {
		this.tophoneNumber = tophoneNumber;
		this.fromphoneNumber = fromphoneNumber;
		this.message = message;
	 }*/
	 
	 /*public Sms(String phoneNumber,
              String message) {
		this.phoneNumber = phoneNumber;
		this.message = message;
	 }

	public Sms() {
		// TODO Auto-generated constructor stub
	}*/
		
		/*public String getFromPhoneNumber() {
			return fromphoneNumber;
		}*/
		
		
		
		/*public String getToPhoneNumber() {
			return tophoneNumber;
		}
		
		public void setToPhoneNumber(String tophoneNumber) {
			this.tophoneNumber = tophoneNumber;
	    }
		
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String message) {
			this.message = message;
	    }*/
}
