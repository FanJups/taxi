package biz.advanceitgroup.taxirideserver.notifications.entities;

import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MessageTemplate extends MainEntity {

    @Id
    @Column(name = "MESSAGE_TEMPLATE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageType; // ALERT, SOS, ACOUNT ACTIVATION, PASSWORD RECOVERY, PAIEMENT SUCCESS

    @Column(length = 100)
    private String object;

    @Column(length = 500)
    private String content;

    private Boolean acceptMail;
    private Boolean acceptSMS;
    private Boolean acceptPushNotification;

}
