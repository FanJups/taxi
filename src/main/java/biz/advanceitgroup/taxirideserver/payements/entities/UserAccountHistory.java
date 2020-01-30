/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author daniel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString
public class UserAccountHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DateModified;
    private long userId;
    private int eventType;
        /*
        1 to cash in money in the account
        2 withdraw money in the account
    */
    private double previewsBalance;
    private double currentBalance;

    public UserAccountHistory(Date DateModified, long userId, int eventType, double previewsBalance, double currentBalance) {
        this.DateModified = DateModified;
        this.userId = userId;
        this.eventType = eventType;
        this.previewsBalance = previewsBalance;
        this.currentBalance = currentBalance;
    }
    
}
