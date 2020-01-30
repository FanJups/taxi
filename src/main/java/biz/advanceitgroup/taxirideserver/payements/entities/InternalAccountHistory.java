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
public class InternalAccountHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long internalAccountHistoryId;
    private long internalAccountId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date mvtDate;
    private double amount;
    private int mvtSource;
    /*
    0:user_deposit
    1:Trippayment
    2:RiderTripCancel
    3:DriverTripCancel
    4:User_withdrawal
    5:InternalDeposit
    6:Internal_withdrawal
    */
    private long tripId;
    private long userDepositId;
    private long userWithdrawalId;
    private long internalDepositId;
    private long internalWithdrawalId;
    private double newBalance;
}
