/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.repository;

import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelCondition;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniel
 */
@Repository
public interface TravelConditionRepository extends JpaRepository<TravelCondition, Long>{
    @Transactional
    @Query(value = "SELECT * FROM `travel_condition` WHERE `travel_id`=?1",nativeQuery = true)
    public Optional<TravelCondition> findTravelConditionByTravelId(long travelId);
}
