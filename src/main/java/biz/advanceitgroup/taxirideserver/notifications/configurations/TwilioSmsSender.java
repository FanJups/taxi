package biz.advanceitgroup.taxirideserver.notifications.configurations;

import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Sms;
import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.SmsRequest;
import biz.advanceitgroup.taxirideserver.notifications.repositories.SmsSender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms {}", smsRequest);
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }

    //send sms
	@Override
	public String sendSmsCode(Sms smsRequest) {
		
		//Production
		final String ACCOUNT_SID =
	            "AC48aace3f0f3ac287b673fdc74383d10f";
	    final String AUTH_TOKEN =
	            "f25f95a335bd2fd1871f4f9198952a91";
	    
	    //Development
	    /*final String ACCOUNT_SID =
	            "ACbb44c9954b87d2760dafe16268ee7db7";
	    final String AUTH_TOKEN =
	            "c7412aa1b00e5dd3f38b30b7f2742794";*/
	    
	    Message message =  null;
		
		if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
			
		     
		     Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		     
		      message = Message
		      
		      .creator(new PhoneNumber(smsRequest.getPhoneNumber()), // to
                      new PhoneNumber("+14804282526"), // from
                      smsRequest.getMessage())
              .create();
		     
		    
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }
		return "Message id: "+message.getSid()+" to: "+smsRequest.getPhoneNumber()+" Message: "+smsRequest.getMessage();
	}
}
