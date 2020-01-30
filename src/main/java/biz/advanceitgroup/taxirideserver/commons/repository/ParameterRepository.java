/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.commons.repository;

import biz.advanceitgroup.taxirideserver.commons.entities.Parameters;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniel
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameters, Long>{
    @Transactional
    @Query(value ="SELECT value_syst FROM error_definition WHERE error_code=?", nativeQuery = true)
    List<String> findParameters(String Key);
    
    @Transactional
    @Query(value ="SELECT value_en FROM `parameters` WHERE `cle`=?", nativeQuery = true)
    long findParameter(String Key);
}
