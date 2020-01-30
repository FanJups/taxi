package biz.advanceitgroup.taxirideserver.profiles.services.interfaces;
import biz.advanceitgroup.taxirideserver.profiles.entities.Vehicle;
import biz.advanceitgroup.taxirideserver.profiles.entities.VehiclePicture;

import java.util.List;
import java.util.Optional;


public interface VehiclePictureService {

    VehiclePicture save(VehiclePicture picture);

    void delete(VehiclePicture picture);

    Optional<VehiclePicture> findOne(String id);

    Optional<VehiclePicture> findOne(Long id);

    Optional<VehiclePicture> findByUrlImage(String url);

    VehiclePicture update(VehiclePicture picture, Long id);

    List<VehiclePicture> findAll();

    List<VehiclePicture> findAllByVehicle(Vehicle vehicle);
    
	public Boolean deletePicture(String username, int picture_number);

	List<VehiclePicture> findAllByUsername(String phoneNumber);

}
