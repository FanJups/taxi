package biz.advanceitgroup.taxirideserver.geolocalisation.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.advanceitgroup.taxirideserver.geolocalisation.entities.City;
import biz.advanceitgroup.taxirideserver.geolocalisation.repositories.CityRepository;
import biz.advanceitgroup.taxirideserver.geolocalisation.services.interfaces.GeolocalisationServices;

/**
 * 
 * @author Fanon Jupkwo
 * */

@Service
public class GeolocalisationServicesImpl implements GeolocalisationServices {
	
	
	@Autowired
	CityRepository cityRepository;
	
	public City findByCityName(String cityName)
	{
		return cityRepository.findByCityName(cityName);
	}
	
	

}
