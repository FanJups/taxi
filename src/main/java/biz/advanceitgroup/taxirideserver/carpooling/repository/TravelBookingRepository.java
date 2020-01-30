/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.repository;

import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelBooking;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniel
 */
@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, Long>{
    
    @Transactional
    @Query(value = "SELECT * FROM `travel_booking` WHERE `booking_travel_pathid`=?1 AND (`departure_date` BETWEEN ?2 AND ?3) AND`travel_status`=?4",nativeQuery = true)
    public List<TravelBooking> findAllLongDistanceTripBooking(long routeID, Date startDate, Date endDate, int bookingStatus);

    @Transactional
    @Query(value = "SELECT * FROM `travel_booking` WHERE `rider_id`=?1",nativeQuery = true)
    public List<TravelBooking> findAllRiderBooking(long riderId);
}
