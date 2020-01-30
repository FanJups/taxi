package biz.advanceitgroup.taxirideserver.profiles.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.EmergencyContact;

import java.util.List;
import java.util.Optional;

public interface EmergencyContactService {

    EmergencyContact save(EmergencyContact contact);

    void delete(EmergencyContact contact);

    Optional<EmergencyContact> findOne(String id);

    Optional<EmergencyContact> findOne(Long id);

    EmergencyContact update(EmergencyContact contact, Long id);

    List<EmergencyContact> findAll();

    List<EmergencyContact> findAllByUser(Users user);

    List<EmergencyContact> findAllByUserAndContactType(Users user, Integer type);

	List<EmergencyContact> verifierNumeroExiste(String string, Long id, String phone);

	public boolean deleteContact(Long id);
}
