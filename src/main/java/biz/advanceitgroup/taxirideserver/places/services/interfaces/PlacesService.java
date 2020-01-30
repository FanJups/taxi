/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.places.services.interfaces;

import biz.advanceitgroup.taxirideserver.places.entities.CityPlaces;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */

public interface PlacesService{
    List<CityPlaces> findNewPlaces(String countryCode, String city, long lastId); 
    
    CityPlaces postNewPlace(CityPlaces place);
}
