/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.notifications.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daniel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationDto {
    
    private String title;
    private String message;
    private String topic;
    private String token;

    public PushNotificationDto(String title, String message, String topic) {
        this.title = title;
        this.message = message;
        this.topic = topic;
    }
    
    
    
}
