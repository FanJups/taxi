/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.services.impl;

import biz.advanceitgroup.taxirideserver.payements.entities.InternalAccount;
import biz.advanceitgroup.taxirideserver.payements.entities.UserAccountHistory;
import biz.advanceitgroup.taxirideserver.payements.entities.UserDeposit;
import biz.advanceitgroup.taxirideserver.payements.entities.UserWithdrawal;
import biz.advanceitgroup.taxirideserver.payements.repository.InternalAccountRepository;
import biz.advanceitgroup.taxirideserver.payements.repository.UserAccountHistoryRepository;
import biz.advanceitgroup.taxirideserver.payements.repository.UserDepositRepository;
import biz.advanceitgroup.taxirideserver.payements.repository.UserWithdrawalRepsository;
import biz.advanceitgroup.taxirideserver.payements.services.interfaces.PayementService;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class PayementServiceImpl implements PayementService{
    
    @Autowired
    private InternalAccountRepository internalAccountRepository;
    
    @Autowired
    private UserDepositRepository userDepositRepository;
    
    @Autowired
    private UserAccountHistoryRepository userAccountHistoryRepository;
    
    @Autowired
    private UserWithdrawalRepsository userWithdrawalRepsository;

    @Override
    public void refillAccount(double amount, int accountType, String accountCode) {
        internalAccountRepository.refillingAccount(amount, accountType,new Date());
    }

    @Override
    public void withdrawMoney(double amount,int channel) {
        internalAccountRepository.withdrawFromAccount(amount,channel ,new Date());
    }

    @Override
    public InternalAccount findById(long id) {
        return internalAccountRepository.findById(id);
    }

    @Override
    public UserDeposit refillUserAccount(long amount, double currentAmount,String payementModeValue, String payementModeName, long payementModeId, long userId) {
        /*Optional<UserDeposit> lastUserDepositOccurence= userDepositRepository.checkIfExist(userId);
        if(lastUserDepositOccurence.isPresent()){
           return userDepositRepository.save(new UserDeposit(userId, new Date(), amount, lastUserDepositOccurence.get().getAmount()+amount, payementModeId, payementModeName, payementModeValue));
        }*/
        return userDepositRepository.save(new UserDeposit(userId, new Date(), amount, currentAmount, payementModeId, payementModeName, payementModeValue));
    }

    @Override
    public UserAccountHistory saveHistoryUserAccount(Date dateModified, long userId, int eventType, double previewsBalance, double currentBalance) {
        return userAccountHistoryRepository.save(new UserAccountHistory(dateModified, userId, eventType, previewsBalance, currentBalance));
    }

    @Override
    public UserWithdrawal withdrawFromUserAccount(long userId, double amount, double newBalance, long payementModeId, String payementName, String paymentValue) {
        return userWithdrawalRepsository.save(new UserWithdrawal(userId, new Date(), amount, newBalance, payementModeId, payementName, paymentValue));
    }

    @Override
    public Optional<UserAccountHistory> checkIfExist(long userId) {
        return userAccountHistoryRepository.checkIfExist(userId);
    }
    
}
