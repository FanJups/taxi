package biz.advanceitgroup.taxirideserver.courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.LogUserLocation;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface LogUserLocationRepository  extends JpaRepository<LogUserLocation, Long> {
    
    @Transactional
    @Query(value ="SELECT * FROM `log_user_location` WHERE `email_or_phone_number`=? ORDER BY id ASC LIMIT 1", nativeQuery = true)
    public Optional<LogUserLocation> getLastUserLocation(String emailOrPhoneNumber);

}
