/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.repository;

import biz.advanceitgroup.taxirideserver.payements.entities.InternalAccount;
import biz.advanceitgroup.taxirideserver.payements.entities.UserDeposit;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author daniel
 */
public interface UserDepositRepository extends JpaRepository<UserDeposit, Long>{
    
    @Transactional
    @Modifying
    @Query(value="UPDATE `internal_account` SET `internal_account_balance`=`internal_account_balance`+ ?1,`last_operation_date`?4 WHERE `account_type`=?2 AND `operator_code`=?3", nativeQuery = true)
    public void refillingAccount(long amount,Date date,String payementModeValue,String payementModeName,long payementModeId,long userId);

    @Transactional
    @Query(value="SELECT * FROM `user_deposit` WHERE `user_id`=? ORDER BY `user_deposit_id` ASC LIMIT 1", nativeQuery = true)
    public Optional<UserDeposit> checkIfExist(long userId);


}
