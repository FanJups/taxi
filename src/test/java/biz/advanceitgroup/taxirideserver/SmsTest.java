package biz.advanceitgroup.taxirideserver;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsTest {

	
	
	 /*public static final String ACCOUNT_SID =
	            "AC48aace3f0f3ac287b673fdc74383d10f";
	    public static final String AUTH_TOKEN =
	            "f25f95a335bd2fd1871f4f9198952a91";
	    */
	    public static final String ACCOUNT_SID =
	            "AC48aace3f0f3ac287b673fdc74383d10f";
	    public static final String AUTH_TOKEN =
	            "f25f95a335bd2fd1871f4f9198952a91";

	    public static void main(String[] args) {
	        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	        Message message = Message
	                .creator(new PhoneNumber("+237695005217"), // to
	                        new PhoneNumber("+14804282526"), // from
	                        "Taxiride code: ")
	                .create();

	        System.out.println(message.getSid());
	    }

}
