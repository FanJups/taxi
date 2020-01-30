/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.repository;

import biz.advanceitgroup.taxirideserver.carpooling.entities.Travel;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniel
 */
@Repository
public interface TravelRepository extends JpaRepository<Travel, Long>{
    
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM `travel` WHERE (`driver_email`=?1 OR`driver_phone`=?1) AND `travel_path_id`=?2",nativeQuery = true)
    public List<Travel> findDriverInfoForroute(String emailOrPhone, long travelPathId);
    
    @Transactional
    @Query(value = "SELECT * FROM `travel` WHERE `driver_email`=?1 OR`driver_phone`=?1",nativeQuery = true)
    public List<Travel>findDriverTravels(String emailOrPhone);
    
    @Transactional
    @Query(value = "SELECT * FROM `travel` WHERE (`driver_email`=?1 OR`driver_phone`=?1) AND `travel_path_id`=?2 AND TIMESTAMPDIFF(HOUR,`departure_date`,`effective_departure_date`)>4 ORDER BY id ASC LIMIT 1", nativeQuery = true)
    public Optional<Travel> getLastCancelTravel(String emailOrPhone,long pathId);
    
    @Transactional
    @Query(value = "SELECT * FROM `travel` WHERE `travel_path_id`=?1 AND`departure_date` BETWEEN ?2 AND ?3",nativeQuery = true)
    public List<Travel> findAllLongDistanceTrip(long routeId, Date startDate, Date endDate);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE `travel` SET `travel_status`=?2 WHERE `id`=?1",nativeQuery = true)
    public void startTrip(long tripId,int status);
}
