package biz.advanceitgroup.taxirideserver.profiles.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Rechercher un véhicule suivant sa plaque d'immatriculation
    Optional<Vehicle> findByMatriculationNumber(String number);

    // Recherche de tous les véhicules associés à un utilisateur
    List<Vehicle> findAllByUser (Users user);

        @Query(value="SELECT * from vehicle WHERE user_id=?", nativeQuery = true)
	Optional<Vehicle> findByUserId(Long user_id);
}
