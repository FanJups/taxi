/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.repository;

import biz.advanceitgroup.taxirideserver.carpooling.entities.PrmTravelCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniel
 */
@Repository
public interface PrmTravelConditionRepository extends JpaRepository<PrmTravelCondition, Long>{
    
}
