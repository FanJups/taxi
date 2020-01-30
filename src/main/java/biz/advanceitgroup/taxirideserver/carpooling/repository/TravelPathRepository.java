/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.repository;

import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelPath;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniel
 */
@Repository
public interface TravelPathRepository extends JpaRepository<TravelPath, Long>{
    @Transactional
    @Query(value = "SELECT * FROM `travel_path` WHERE `departure_city`=?1 AND`arrival_city`=?2",nativeQuery = true)
    public Optional<TravelPath> findTravelPath(String startCity,String endCity);
    
    @Transactional
    @Query(value = "SELECT `departure_city` FROM `travel_path` WHERE `country_code`=?1",nativeQuery = true)
    public Optional<List<String>>  findDepartureCities(String countryCode);
    
    //find ArrivalCities, Costs and Distances
    
    @Transactional
    public Optional<List<TravelPath>>  findByDepartureCityAndCountryCode(String departureCity,String countryCode);
}
