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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserWithdrawal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userWithdrawalId;
    private long userId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date withdrawalDate;
    private double amount;
    private double newBalance;
    private long payementModeId;
    private String payementName;
    private String paymentValue;

    public UserWithdrawal(long userId, Date withdrawalDate, double amount, double newBalance, long payementModeId, String payementName, String paymentValue) {
        this.userId = userId;
        this.withdrawalDate = withdrawalDate;
        this.amount = amount;
        this.newBalance = newBalance;
        this.payementModeId = payementModeId;
        this.payementName = payementName;
        this.paymentValue = paymentValue;
    }
    
}
