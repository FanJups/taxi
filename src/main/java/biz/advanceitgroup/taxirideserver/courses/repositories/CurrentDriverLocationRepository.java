package biz.advanceitgroup.taxirideserver.courses.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.CurrentDriverLocation;
import java.util.Date;

@Repository
public interface CurrentDriverLocationRepository extends JpaRepository<CurrentDriverLocation, Long> {
	
	
	//@Modifying(clearAutomatically = true)
  	//@Transactional
	
	//@Query(value ="select * from current_driver_location where code_pays = ? and code_ville = ? and driver_available = 1 and time_to_sec(timediff(now(), date_enreg))/60 <= timeout_driver_connect and #RAYON# <= 12742 * atan2(sqrt(sin( (lattitude - #LATTITUDE#) * PI() / 360) * sin( (lattitude - #LATTITUDE#) * PI() / 360) + sin( (longitude - #LONGITUDE#) * PI() / 360) * sin( (longitude - #LONGITUDE#) * PI() / 360) * cos(#LATTITUDE#) * cos(lattitude)), sqrt(1 - sin( (lattitude - #LATTITUDE#) * PI() / 360) * sin( (lattitude - #LATTITUDE#) * PI() / 360) + sin( (longitude - #LONGITUDE#) * PI() / 360) * sin( (longitude - #LONGITUDE#) * PI() / 360) * cos(#LATTITUDE#) * cos(lattitude)))", nativeQuery = true)
	//@Query(value ="SELECT ( 6371.01 * ( acos( cos( RADIANS(#lattitude#) ) * cos( RADIANS(latitude) ) * cos( RADIANS(#longitude#) - radians(LONGITUDE) ) + sin( RADIANS(#LATTITUDE#) ) * sin( radians(lattitude) ) ) ) AS distance, id , ride_id , code_pays , date_enreg , email_or_phone_number, latitude, longitude from current_driver_location where code_pays = ? and ville = ? and driver_available = 1 HAVING distance < ? ORDER BY distance", nativeQuery = true)
        @Query(value ="SELECT ( 6371.01 * ( acos( cos( RADIANS(latitude) ) * cos( RADIANS(?4) ) * cos( RADIANS(longitude) - RADIANS(?3) ) + sin( RADIANS(latitude) ) * sin( RADIANS(?4) )))) AS distance, id , ride_id , code_pays , date_enreg ,timeout_driver_connect,driver_available, email_or_phone_number, latitude, longitude,ville,position,duration from current_driver_location where code_pays = ?1 and ville = ?2 and driver_available=1 HAVING distance < ?5 ORDER BY distance", nativeQuery = true)
	List<CurrentDriverLocation> findAllAvailableDrivers(String codepays, String codeville, double longitude, double latitude, long rayon);
	//List<CurrentDriverLocation> findAllAvailableDrivers(String codepays, String codeville, long rayon);
	
        @Modifying(clearAutomatically = true)
  	@Transactional
        @Query(value ="UPDATE current_driver_location SET ride_id=?8,code_pays=?2,distance=CASE WHEN current_driver_location.distance=0 then 0 ELSE distance- (SELECT  6371.01 * ( acos( cos( RADIANS(current_driver_location.latitude) ) * cos( RADIANS(?5) ) * cos( RADIANS(current_driver_location.longitude) - RADIANS(?4) ) + sin( RADIANS(current_driver_location.latitude) ) * sin( RADIANS(?5) ))))END ,driver_available=?9,duration=CASE WHEN current_driver_location.distance=0 then 0 ELSE distance/(SELECT current_driver_location.distance/TIMESTAMPDIFF(second,current_driver_location.date_enreg,?11)) END,email_or_phone_number=?10,latitude=?5,longitude=?4,position=?6,timeout_driver_connect=?1,ville=?3,date_enreg=now() WHERE id=?7", nativeQuery = true)
	void updateDriverLocation(long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, String position,long id, long rideId,Boolean driverAvailable, String emailOrPhone,Date date_enreg);
        
        @Modifying(clearAutomatically = true)
  	@Transactional
        @Query(value ="UPDATE current_driver_location SET code_pays=?2,distance=CASE WHEN current_driver_location.distance=0 then 0 ELSE distance- (SELECT  6371.01 * ( acos( cos( RADIANS(current_driver_location.latitude) ) * cos( RADIANS(?5) ) * cos( RADIANS(current_driver_location.longitude) - RADIANS(?4) ) + sin( RADIANS(current_driver_location.latitude) ) * sin( RADIANS(?5) ))))END ,driver_available=?8,duration=CASE WHEN current_driver_location.distance=0 then 0 ELSE distance/(SELECT current_driver_location.distance/TIMESTAMPDIFF(second,current_driver_location.date_enreg,?10)) END,email_or_phone_number=?9,latitude=?5,longitude=?4,position=?6,timeout_driver_connect=?1,ville=?3,date_enreg=now() WHERE id=?7", nativeQuery = true)
	void updateDriverLocationWithoutRideid(long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, String position,long id,Boolean driverAvailable, String emailOrPhone,Date date_enreg);
        
        @Modifying(clearAutomatically = true)
  	@Transactional
        @Query(value ="UPDATE current_driver_location SET code_pays=?2,distance=CASE WHEN current_driver_location.distance=0 then 0 ELSE distance- (SELECT  6371.01 * ( acos( cos( RADIANS(current_driver_location.latitude) ) * cos( RADIANS(?5) ) * cos( RADIANS(current_driver_location.longitude) - RADIANS(?4) ) + sin( RADIANS(current_driver_location.latitude) ) * sin( RADIANS(?5) ))))END ,duration=CASE WHEN current_driver_location.distance=0 then 0 ELSE distance/(SELECT current_driver_location.distance/TIMESTAMPDIFF(second,current_driver_location.date_enreg,?8)) END,latitude=?5,longitude=?4,position=?6,timeout_driver_connect=?1,ville=?3,date_enreg=now() WHERE email_or_phone_number=?7", nativeQuery = true)
	void updateDriverLocationWithoutRideId(long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, String position, String emailOrPhone,Date date_enreg);
        
        
        @Modifying(clearAutomatically = true)
  	@Transactional
        @Query(value ="UPDATE current_driver_location SET ride_id=?5,distance=(SELECT  6371.01 * ( acos( cos( RADIANS(trip.driver_start_lattitude ) ) * cos( RADIANS(?3) ) * cos( RADIANS(trip.driver_start_longitude) - RADIANS(?2) ) + sin( RADIANS(trip.driver_start_lattitude ) ) * sin( RADIANS(?3) ))) FROM trip WHERE trip_id=?5) ,driver_available=?6,duration=case when( TIMESTAMPDIFF(second,current_driver_location.date_enreg,now())=0 OR current_driver_location.distance=0) THEN 0 ELSE distance/(SELECT current_driver_location.distance/TIMESTAMPDIFF(second,current_driver_location.date_enreg,now()))END,latitude=?3,longitude=?2,position=?4,timeout_driver_connect=?1,date_enreg=now() WHERE email_or_phone_number=?7", nativeQuery = true)
	void updateDriverLocationForAcceptTrip(long timeoutDriverConnect, double longitude, double latitude, String position, long rideId,Boolean driverAvailable, String emailOrPhone);
        
	@Query(value ="SELECT * FROM current_driver_location WHERE email_or_phone_number=? ORDER BY ID DESC limit 1", nativeQuery = true)
	Optional<CurrentDriverLocation> findDriverLocation(String phone);
	
    /**
     *
     * @param rideId
     * @param id
     */
    @Modifying(clearAutomatically = true)
  	@Transactional
	@Query(value ="UPDATE current_driver_location SET ride_id=? WHERE id=?", nativeQuery = true)
	void acceptRide(long rideId, Long id);
	
    /**
     *
     * @param longitude
     * @param latitude
     * @param emailOrPhone
     */
    @Modifying(clearAutomatically = true)
  	@Transactional
        @Query(value ="UPDATE current_driver_location SET distance=CASE WHEN current_driver_location.distance=0 then 0 ELSE distance- (SELECT  6371.01 * ( acos( cos( RADIANS(current_driver_location.latitude) ) * cos( RADIANS(?2) ) * cos( RADIANS(current_driver_location.longitude) - RADIANS(?1) ) + sin( RADIANS(current_driver_location.latitude) ) * sin( RADIANS(?2) ))))END ,duration=CASE WHEN current_driver_location.duration=0 then 0 ELSE distance/(SELECT current_driver_location.distance/TIMESTAMPDIFF(second,current_driver_location.date_enreg,now())) END,latitude=?2,longitude=?1,date_enreg=now() WHERE email_or_phone_number=?3", nativeQuery = true)
	void updateSimpleDriverLocation(double longitude, double latitude,String emailOrPhone);
         
        @Transactional
        @Query(value ="SELECT * from current_driver_location WHERE ride_id=?", nativeQuery = true)
	Optional<CurrentDriverLocation> findCurrentRideDriverLocation(long tripId);
        

}