/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.commons.repository;

import biz.advanceitgroup.taxirideserver.commons.entities.ErrorDefinition;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniel
 */
@Repository
public interface ErrorDefinitionRepository extends JpaRepository<ErrorDefinition, Long>{
    
    @Transactional
    @Query(value ="SELECT value_syst FROM error_definition WHERE error_code=?", nativeQuery = true)
    String findErrorByCode(long code);
    
    @Transactional
    @Query(value ="SELECT value_fr FROM error_definition WHERE error_code=?", nativeQuery = true)
    String findErrorByCodeFr(long code);
    
    @Transactional
    @Query(value ="SELECT value_en FROM error_definition WHERE error_code=?", nativeQuery = true)
    String findErrorByCodeEn(long code);
}
