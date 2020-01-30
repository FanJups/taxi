/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.repository;

import biz.advanceitgroup.taxirideserver.payements.entities.InternalAccount;
import biz.advanceitgroup.taxirideserver.payements.entities.UserWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author daniel
 */
public interface UserWithdrawalRepsository extends JpaRepository<UserWithdrawal, Long>{
    
}
