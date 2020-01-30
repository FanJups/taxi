package biz.advanceitgroup.taxirideserver.courses.repositories;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.Trip;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
        @Transactional
        @Query(value ="SELECT * FROM trip WHERE trip_id=?", nativeQuery = true)
        Optional<Trip> findByTripId (Long id);
	//@Modifying(clearAutomatically = true)
        
         @Transactional
        @Query(value ="SELECT *FROM trip WHERE (`driver_email`=?1 OR`driver_phone`=?1 OR`rider_email`=?1 OR`rider_phone`=?1) AND created_at BETWEEN ?2 AND ?3", nativeQuery = true)
        List<Trip> findAllTrip (String emailOrPhone, Date startDate, Date endDate);
        
  	@Modifying
        @Transactional
  	@Query(value ="UPDATE trip SET driver_address = ?1, driver_email = ?2, driver_phone = ?3,  driver_start_lattitude = ?4 ,driver_start_longitude = ?5, trip_status=?6,updated_at=now() WHERE trip_id = ?7", nativeQuery = true)
	void acceptRideUpdateDriver(String driverAddress, String driverEmail, String driverPhone, double driverStartLattitude, double driverStartLongitude, int tripStatus, Long trip_id, double durationBeforePickUp);
       
        /*
        *update the trip cost
        *
        */
        @Modifying
        @Transactional
  	@Query(value ="UPDATE trip SET trip_cost =?2,trip_cancel_cost=0 WHERE trip_id = ?1", nativeQuery = true)
	void updateTripCost(long tripId, int tripCost);
       
        
        /*
        *
        *
        */
        @Modifying
        @Transactional
        @Query(value ="UPDATE trip set estimated_trip_distance= 6371.01 * ( acos( cos( RADIANS(trip.arrival_latitude) ) * cos( RADIANS(trip.pickup_latitude) ) * cos( RADIANS(trip.arrival_longitude) - RADIANS(trip.pickup_longitude) ) + sin( RADIANS(trip.arrival_latitude) ) * sin( RADIANS(trip.pickup_latitude) ))) where trip_id=?" , nativeQuery = true)
        void updateTrip(long id);
        /*
        *start a trip
        *
        */
         @Modifying
        @Transactional
        @Query(value ="UPDATE trip set trip_status=3, pickup_leave_date=now()where trip_id=?1 AND (driver_email=?2 OR driver_phone=?2)" , nativeQuery = true)
        void startTrip(long tripId, String emailOrPhone);
        /*
        *end a trip by a driver
        *
        */
         @Modifying
        @Transactional
        @Query(value ="UPDATE trip set trip_status=4,arrival_date=now() where trip_id=?1 AND (driver_email=?2 OR driver_phone=?2) " , nativeQuery = true)
        void endTripByDriver(long tripId, String driverUsername);
        /*
        *cancel a trip by a driver
        *
        */
        @Modifying
        @Transactional
        @Query(value ="UPDATE trip set trip_status=1,trip_cancel_role =2,trip_cancel_cost=?3,trip_cancel_date=now() where trip_id=?1 AND (driver_email=?2 OR driver_phone=?2) " , nativeQuery = true)
        void cancelTripByDriver(long tripId, String driverUsername,long tripCancelCost);
        /*
        *cancel a trip by a rider
        *
        */
        @Modifying
        @Transactional
        @Query(value ="UPDATE trip set trip_status=1,trip_cancel_role =1,trip_cancel_cost=?3,trip_cancel_date=now() where trip_id=?1 AND (rider_email=?2 OR rider_phone=?2) " , nativeQuery = true)
        void cancelTripByRider(long tripId, String riderUsername,long tripCancelCost);
        /*
        *compute the trip cost
        *
        */
        @Transactional
        @Query(value ="SELECT trip_cost from trip WHERE trip_id=?" , nativeQuery = true)
        double getTripCost(long tripId);
        
        /*
        *compute the fee allowed when a trip is cancelled by a driver
        *
        */
        @Modifying
        @Transactional
        @Query(value ="SELECT (SELECT trip_cancel_cost  from trip) where trip_id=?5" , nativeQuery = true)
        double getCanceTripCostByDriver(String emailorphone,String countryCode,String city,String codeOption,long tripID);
        
        /*
        *compute the fee allowed when a trip is cancelled by a rider
        *
        */
         @Modifying
        @Transactional
        @Query(value ="SELECT trip_cancel_cost  from trip" , nativeQuery = true)
        double getCanceTripCostByRider(String emailorphone,String countryCode,String city,String codeOption,long tripID);
        
        
       @Transactional
        @Query(value ="SELECT `base_fare`+(?2) *`kilometer_rate`+(?3) *`minute_rate` FROM `trip_option` WHERE `option_code`=?1" , nativeQuery = true)
        double getSampleTripCost(String optionCode,long estimatedDistance,long estimatedDuration);
        
        @Transactional
        @Query(value ="SELECT `trip_status` FROM `trip` WHERE `trip_id`=?",nativeQuery = true)
        int getTripStatus(long tripId);
}
