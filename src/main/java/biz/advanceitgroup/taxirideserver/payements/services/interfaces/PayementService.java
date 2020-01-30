/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.services.interfaces;

import biz.advanceitgroup.taxirideserver.payements.entities.InternalAccount;
import biz.advanceitgroup.taxirideserver.payements.entities.UserAccountHistory;
import biz.advanceitgroup.taxirideserver.payements.entities.UserDeposit;
import biz.advanceitgroup.taxirideserver.payements.entities.UserWithdrawal;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author daniel
 */
public interface PayementService {
    
    public void refillAccount(double amount,int accountType,String accountCode);
    
    public UserDeposit refillUserAccount(long amount,double currentAmount,String payementModeValue,String payementModeName,long payementModeId,long userId);
    
    public void withdrawMoney(double amount ,int channel);
    
    public UserWithdrawal withdrawFromUserAccount(long userId, double amount, double newBalance, long payementModeId, String payementName, String paymentValue);
    
    public InternalAccount findById(long id);
    
    public UserAccountHistory saveHistoryUserAccount(Date dateModified,long userId,int eventType, double previewsBalance, double currentBalance);
    
    public Optional<UserAccountHistory> checkIfExist(long userId);
}
