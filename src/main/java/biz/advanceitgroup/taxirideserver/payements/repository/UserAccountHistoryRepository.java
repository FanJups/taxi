/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.repository;

import biz.advanceitgroup.taxirideserver.payements.entities.UserAccountHistory;
import biz.advanceitgroup.taxirideserver.payements.entities.UserDeposit;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author daniel
 */
public interface UserAccountHistoryRepository extends JpaRepository<UserAccountHistory, Long>{
    @Transactional
    @Query(value="SELECT * FROM `user_account_history` WHERE `user_id`=? ORDER BY `id` ASC LIMIT 1", nativeQuery = true)
    public Optional<UserAccountHistory> checkIfExist(long userId);
}
