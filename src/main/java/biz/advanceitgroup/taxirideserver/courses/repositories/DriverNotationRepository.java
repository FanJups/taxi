package biz.advanceitgroup.taxirideserver.courses.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.DriverNotation;

@Repository
public interface DriverNotationRepository  extends JpaRepository<DriverNotation, Long> {
	
	@Query(value ="SELECT * FROM driver_notation WHERE driverid=? ORDER BY ID DESC LIMIT 5", nativeQuery = true)
	List<DriverNotation> findAllDriverReviews(long driverid);
}
