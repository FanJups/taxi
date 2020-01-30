package biz.advanceitgroup.taxirideserver.notifications.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifications{
    private String title;
    private String message;
    private String topic;
    private String token;

    public Notifications(String title, String message, String topic) {
        this.title = title;
        this.message = message;
        this.topic = topic;
    }

    public Notifications(String title, String message) {
        this.title = title;
        this.message = message;
    }  
}
