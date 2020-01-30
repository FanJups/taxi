package biz.advanceitgroup.taxirideserver.profiles.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Vehicle save(Vehicle vehicle);

    void delete(Vehicle vehicle);

    Optional<Vehicle> findOne(String id);

    Optional<Vehicle> findOne(Long id);

    Optional<Vehicle> findByImmatriculationNumber(String Inumber);

    Vehicle update(Vehicle vehicle, Long id);

    List<Vehicle> findAll();

    List<Vehicle> findAllByUser(Users user);

	Optional<Vehicle> findByUserId(Long user_id);

}
