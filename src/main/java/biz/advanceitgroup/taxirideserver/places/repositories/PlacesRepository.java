/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.places.repositories;

import biz.advanceitgroup.taxirideserver.places.entities.CityPlaces;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniel
 */
@Repository
public interface PlacesRepository extends JpaRepository<CityPlaces, Long>{
    
    @Query(value ="SELECT * FROM city_places WHERE country_code=?1 AND city=?2 AND place_id>?3", nativeQuery = true)
    List<CityPlaces> findNewPlaces(String countryCode, String city, long lastId); 
    
    
}
