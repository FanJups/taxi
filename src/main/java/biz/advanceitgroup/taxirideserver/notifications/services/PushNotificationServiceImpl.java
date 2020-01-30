/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.notifications.services;

import biz.advanceitgroup.taxirideserver.notifications.fcm.FirebaseService;
import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Notifications;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class PushNotificationServiceImpl {
    
    @Autowired
    private FirebaseService firebaseService;
    
     @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;

    public PushNotificationServiceImpl(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void sendSamplePushNotification() {
        try { 
            firebaseService.sendMessageWithoutData(getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PushNotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }
    }

    public void sendPushNotification(Notifications request) {
   
        try {
            firebaseService.sendMessage(getSamplePayloadData(), request);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PushNotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }
        
    }

    public void sendPushNotificationWithoutData(Notifications request) {
        try {
            firebaseService.sendMessageWithoutData(request);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PushNotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }
       
    }


    public void sendPushNotificationToToken(Notifications request) {
      
        try {
            firebaseService.sendMessageToToken(request);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PushNotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }
       
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", defaults.get("payloadMessageId"));
        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
        return pushData;
    }


    private Notifications getSamplePushNotificationRequest() {
        Notifications request = new Notifications(defaults.get("title"),
                defaults.get("message"),
                defaults.get("topic"));
        return request;
    }
}
