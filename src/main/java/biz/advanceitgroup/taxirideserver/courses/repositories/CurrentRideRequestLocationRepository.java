package biz.advanceitgroup.taxirideserver.courses.repositories;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.CurrentRideRequestLocation;

@Repository
public interface CurrentRideRequestLocationRepository extends JpaRepository<CurrentRideRequestLocation, Long> {
	
	@Query(value ="SELECT * FROM current_ride_request_location WHERE trip_id=? limit 1", nativeQuery = true)
  	Optional<CurrentRideRequestLocation> checkDriverExist(long tripId);
        
        @Query(value ="SELECT * FROM current_ride_request_location WHERE trip_id=? limit 1", nativeQuery = true)
  	Optional<CurrentRideRequestLocation> checkIfDriverExist(Long id);

        //@Query(value ="SELECT ( 6371.01 * ( acos( cos( RADIANS(latitude) ) * cos( RADIANS(?1) ) * cos( RADIANS(longitude) - RADIANS(?2) ) + sin( RADIANS(latitude) ) * sin( RADIANS(?1) ))))  AS distance, id, code_pays,date_enreg, driverid, emailorphone, latitude, longitude, rayon, ride_duration_before_pick_up,trip_id, trip_optionid, ville from current_ride_request_location where code_pays=?3 and ville = ?4 HAVING distance < ?5 ORDER BY distance", nativeQuery = true)
	//@Query(value ="SELECT ( 6371 * acos( cos( radians(37) ) * cos( radians( lattitude ) ) * cos( radians( longitude ) - radians(-122) ) + sin( radians(37) ) * sin( radians( lattitude ) ) ) ) AS distance, id, date_enreg, driverid, emailorphone, lattitude, longitude, rayon, ride_duration_before_pick_up, trip_optionid, ville from current_ride_request_location where emailorphone = ? and ville = ? HAVING distance < ? ORDER BY distance", nativeQuery = true)
        @Query(value ="SELECT distance,(CASE WHEN distance<=?4 THEN true ELSE false END) AS is_in_the_range, id, code_pays,date_enreg, driverid, emailorphone, latitude, longitude, rayon, ride_duration_before_pick_up,trip_id, trip_optionid, ville FROM (SELECT ( 6371.01 * ( acos( cos( RADIANS(latitude) ) * cos( RADIANS(?1) ) * cos( RADIANS(longitude) - RADIANS(?2) ) + sin( RADIANS(latitude) ) * sin( RADIANS(?1)))))  AS distance ,  is_in_the_range, id, code_pays,date_enreg, driverid, emailorphone, latitude, longitude, rayon, ride_duration_before_pick_up,trip_id, trip_optionid, ville FROM current_ride_request_location where ville = ?3) AS calcul where driverid=0 ORDER BY distance", nativeQuery = true)
        List<CurrentRideRequestLocation> findAllAvailableTrip(double lattitude,double longitude,String city, double rayon);
	
	//(SELECT duration  FROM current_driver_location WHERE email_or_phone_number=?3 )
	@Modifying(clearAutomatically = true)
  	@Transactional
	@Query(value ="UPDATE current_ride_request_location SET driverid=?1 ,ride_duration_before_pick_up=?3 WHERE id=?2", nativeQuery = true)
	void acceptRide(long driverID, long id,double durationBeforePickUp);
        
        @Modifying(clearAutomatically = true)
  	@Transactional
	@Query(value ="DELETE FROM `current_ride_request_location` WHERE `trip_id`=?", nativeQuery = true)
	void deleteByTripId(long id);
}
