package biz.advanceitgroup.taxirideserver.notifications.services;

import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Sms;
import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.SmsRequest;
import biz.advanceitgroup.taxirideserver.notifications.repositories.SmsSender;
import biz.advanceitgroup.taxirideserver.notifications.configurations.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class SmsService {

    private final SmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }
    
    public String sendSmsCode(Sms smsRequest) {
		return smsSender.sendSmsCode(smsRequest);
    }
}
