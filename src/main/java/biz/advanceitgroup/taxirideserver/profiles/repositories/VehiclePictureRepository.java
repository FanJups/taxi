package biz.advanceitgroup.taxirideserver.profiles.repositories;

import biz.advanceitgroup.taxirideserver.profiles.entities.Vehicle;
import biz.advanceitgroup.taxirideserver.profiles.entities.VehiclePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
public interface VehiclePictureRepository extends JpaRepository<VehiclePicture, Long> {

    // Retrouver une image de véhicule à partir de son url
    Optional<VehiclePicture> findByPictureURL(String url);

    // Rechercher toutes les images associées à un véhicule
    List<VehiclePicture> findAllByVehicle(Vehicle vehicle);

    //delet vehicule picture
    @Modifying(clearAutomatically = true)
  	@Transactional
  	//@Query("delete from VehiclePicture v where v.username=:x and v.pictureNumber=:y")
    @Query(value ="DELETE FROM vehicle_picture WHERE username=? AND picture_number=? ", nativeQuery = true)
	//Boolean deletePicture(@Param("x") String username, @Param("y") int picture_number);
    int deletePicture(String username, int picture_number);
    
 // Rechercher toutes les images associées à un user
    @Modifying(clearAutomatically = true)
  	@Transactional
  	//@Query("delete from VehiclePicture v where v.username=:x and v.pictureNumber=:y")
    @Query(value ="SELECT * FROM vehicle_picture WHERE username=?", nativeQuery = true)
    List<VehiclePicture> findAllByUsername(String phoneNumber);

}
