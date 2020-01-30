package biz.advanceitgroup.taxirideserver.notifications.repositories;

import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Sms;
import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

	String sendSmsCode(Sms smsRequest);

}
