/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
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
public class InternalAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long internalAccountId;
    
    private String accountNumber;
    private String accountName;
    private int accountType;
        /*
        0:Mobile account
        1:wallet
        3: credit card
        */
    private String operatorCode;
    private String description;
    private double internalAccountBalance;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastOperationDate;

    public InternalAccount(double userAccountBalance, Date lastOperationDate, double internalAccountBalance) {
        this.lastOperationDate = lastOperationDate;
        this.internalAccountBalance=internalAccountBalance;
    }

    public InternalAccount( String accountName, int accountType, String operatorCode, String description, Date lastOperationDate, double internalAccountBalance) {
        if(accountType==0){
          this.accountNumber ="MO"+this.internalAccountId;
          this.accountName = accountName;
          this.accountType = accountType;
          this.operatorCode = operatorCode;
          this.description = description;
          this.lastOperationDate = lastOperationDate;
          this.internalAccountBalance=internalAccountBalance;
        }
      else if(accountType==2){
        this.accountNumber ="WA"+internalAccountId ;
          this.accountName = accountName;
          this.accountType = accountType;
          this.operatorCode = operatorCode;
          this.description = description;
          this.lastOperationDate = lastOperationDate;
          this.internalAccountBalance=internalAccountBalance;
      }
      else if(accountType==3){
          this.accountNumber = "CA"+this.internalAccountId;
          this.accountName = accountName;
          this.accountType = accountType;
          this.operatorCode = operatorCode;
          this.description = description;
          this.lastOperationDate = lastOperationDate;
        this.internalAccountBalance=internalAccountBalance;
      }
    }
}
