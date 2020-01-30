package biz.advanceitgroup.taxirideserver.geolocalisation.services.interfaces;



import org.springframework.stereotype.Service;

import biz.advanceitgroup.taxirideserver.geolocalisation.entities.City;

@Service
public interface GeolocalisationServices {
	
	 
	// Find city by its name
	
	public City findByCityName(String cityName);
	
	

}
