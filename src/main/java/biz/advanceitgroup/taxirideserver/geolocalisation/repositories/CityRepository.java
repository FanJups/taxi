package biz.advanceitgroup.taxirideserver.geolocalisation.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import biz.advanceitgroup.taxirideserver.geolocalisation.entities.City;


/**
*
* @author Fanon Jupkwo
*/
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	@Transactional
	public City findByCityName(String cityName);
	
	
	
	

}
