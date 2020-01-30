/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.repository;

import biz.advanceitgroup.taxirideserver.payements.entities.InternalAccount;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniel
 */
@Repository
public interface InternalAccountRepository extends JpaRepository<InternalAccount, Long>{
    
    @Transactional
    @Query(value="SELECT * FROM `internal_account` WHERE `account_id`=?", nativeQuery = true)
     public InternalAccount findById(long id);
     
    @Transactional
    @Modifying
    @Query(value="UPDATE `internal_account` SET `internal_account_balance`=`internal_account_balance`+ ?1,`last_operation_date`=?3 WHERE `account_type`=?2", nativeQuery = true)
    public void refillingAccount(double amount,int AccountType,Date date);
    
    @Transactional
    @Modifying
    @Query(value="UPDATE `internal_account` SET `internal_account_balance`=`internal_account_balance`- ?1,`last_operation_date`=?3 WHERE `account_type`=?2", nativeQuery = true)
    public void withdrawFromAccount(double amount,int AccountType,Date date);
}
