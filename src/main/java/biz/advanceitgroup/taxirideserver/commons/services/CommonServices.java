/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.commons.services;

import biz.advanceitgroup.taxirideserver.commons.entities.Parameters;
import java.util.List;

/**
 * service which is in charge of the mangement of the commun packages of the entire application; Eg: error managment, global variables, etc...
 * @author daniel
 */
public interface CommonServices {
    
    
    //void postError(ErrorDefinition errorDefinition);
    //finds the description of a specific error present in the error_description table through its code;
    String findErrorByCode(long code,String language);
    
    List<Parameters> findAllParameters();
    
    long findParameter(String cle);
}
