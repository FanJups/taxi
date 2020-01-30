/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.commons.servicesimpl;

import biz.advanceitgroup.taxirideserver.commons.entities.ErrorDefinition;
import biz.advanceitgroup.taxirideserver.commons.entities.Parameters;
import biz.advanceitgroup.taxirideserver.commons.repository.ErrorDefinitionRepository;
import biz.advanceitgroup.taxirideserver.commons.repository.ParameterRepository;
import biz.advanceitgroup.taxirideserver.commons.services.CommonServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class CommonServicesImpl implements CommonServices{
    
    @Autowired
    ErrorDefinitionRepository errorDefinitionRepository;
    
    @Autowired
    ParameterRepository parameterRepository;
    /*@Override
    public void postError(ErrorDefinition errorDefinition) {
        errorDefinitionRepository.save(errorDefinition);
    }*/

    @Override
    public String findErrorByCode(long code,String language) {
        if(language.equalsIgnoreCase("en"))
             return errorDefinitionRepository.findErrorByCodeEn(code);
        else if(language.equalsIgnoreCase("fr"))
            return errorDefinitionRepository.findErrorByCodeFr(code);
        else
            return errorDefinitionRepository.findErrorByCode(code);
    }

    @Override
    public List<Parameters> findAllParameters() {
        return parameterRepository.findAll();
    }

    @Override
    public long findParameter(String cle) {
        return parameterRepository.findParameter(cle);
    }
}
