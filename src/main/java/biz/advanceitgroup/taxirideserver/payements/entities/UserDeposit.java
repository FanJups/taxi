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
public class UserDeposit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userDepositId;
    private long userId;
    private Date depositDate;
    private double amount;
    private double newBalance;
    private long payementModeId;
    private String payementModeName;
    private String payementModeValue;

    public UserDeposit(long userId, Date depositDate, double amount, double newBalance, long payementModeId, String payementModeName, String payementModeValue) {
        this.userId = userId;
        this.depositDate = depositDate;
        this.amount = amount;
        this.newBalance = newBalance;
        this.payementModeId = payementModeId;
        this.payementModeName = payementModeName;
        this.payementModeValue = payementModeValue;
    }
    
    
}
