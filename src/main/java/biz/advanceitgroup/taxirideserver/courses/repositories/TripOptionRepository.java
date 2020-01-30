package biz.advanceitgroup.taxirideserver.courses.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.TripOption;

@Repository
public interface TripOptionRepository extends JpaRepository<TripOption, Long> {
	
  	@Query(value ="SELECT * FROM trip_option WHERE option_code=?1 AND city_code=?2", nativeQuery = true)
	Optional<TripOption> findTripOptionByCode(String option_code, String cityCode);
	
	@Query(value ="SELECT option_code  FROM trip_option WHERE country_code =? AND city_code =?", nativeQuery = true)
	List<String> findAvailableOptionsByCity(String countryCode, String cityCode);
        
        @Query(value ="SELECT * FROM trip_option WHERE option_code =? AND city_code =?", nativeQuery = true)
	Optional<TripOption> findOptionsByCityAndCode(String optionCode, String cityCode);
}
