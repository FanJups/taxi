/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.places.services.impl;

import biz.advanceitgroup.taxirideserver.places.entities.CityPlaces;
import biz.advanceitgroup.taxirideserver.places.services.interfaces.PlacesService;
import java.util.List;
import org.springframework.stereotype.Service;
import biz.advanceitgroup.taxirideserver.places.repositories.PlacesRepository;

/**
 *
 * @author daniel
 */
@Service
public class PlacesServiceImpl implements PlacesService{
    
    PlacesRepository placesRepository;

    @Override
    public List<CityPlaces> findNewPlaces(String countryCode, String city, long lastId) {
        return placesRepository.findNewPlaces(countryCode, city, lastId);
    }

    @Override
    public CityPlaces postNewPlace(CityPlaces place) {
        return placesRepository.save(place);
    }
    
}
