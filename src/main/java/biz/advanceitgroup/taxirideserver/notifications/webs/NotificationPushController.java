package biz.advanceitgroup.taxirideserver.notifications.webs;


import biz.advanceitgroup.taxirideserver.notifications.fcm.PushNotificationsResponse;
import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Notifications;
import biz.advanceitgroup.taxirideserver.notifications.services.PushNotificationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Notification Rest API")
@RestController
@RequestMapping("/api/pushNotification")
public class NotificationPushController {
    
    @Autowired
    private PushNotificationServiceImpl pushNotificationServiceImpl;
    
    @ApiOperation(value = "Send Notifications to a group of devices")
    @PostMapping("/notification/topic")
    public ResponseEntity sendNotification(@RequestBody Notifications request) {
        pushNotificationServiceImpl.sendPushNotificationWithoutData(request);
        return new ResponseEntity<>(new PushNotificationsResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    @ApiOperation(value = "Send notification to a given device")
    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody Notifications request) {
        pushNotificationServiceImpl.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationsResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    @ApiOperation(value = "Send notification with data loaded")
    @PostMapping("/notification/data")
    public ResponseEntity sendDataNotification(@RequestBody Notifications request) {
        pushNotificationServiceImpl.sendPushNotification(request);
        return new ResponseEntity<>(new PushNotificationsResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    @ApiOperation(value = "Send Sample notification..")
    @GetMapping("/notification")
    public ResponseEntity sendSampleNotification() {
        pushNotificationServiceImpl.sendSamplePushNotification();
        return new ResponseEntity<>(new PushNotificationsResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

	
}
