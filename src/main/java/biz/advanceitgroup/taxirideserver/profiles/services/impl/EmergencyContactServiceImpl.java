package biz.advanceitgroup.taxirideserver.profiles.services.impl;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.EmergencyContact;
import biz.advanceitgroup.taxirideserver.profiles.repositories.EmergencyContactRepository;
import biz.advanceitgroup.taxirideserver.profiles.services.interfaces.EmergencyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyContactServiceImpl implements EmergencyContactService {

    @Autowired
    EmergencyContactRepository emergencyContactRepository;

    @Override
    public EmergencyContact save(EmergencyContact contact) {
        return emergencyContactRepository.save(contact);
    }

    @Override
    public void delete(EmergencyContact contact) {
        emergencyContactRepository.delete(contact);
    }

    @Override
    public Optional<EmergencyContact> findOne(String id) {
        return emergencyContactRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<EmergencyContact> findOne(Long id) {
        return emergencyContactRepository.findById(id);
    }

    @Override
    public EmergencyContact update(EmergencyContact contact, Long id) {
        contact.setId(id);
        return save(contact);
    }

    @Override
    public List<EmergencyContact> findAll() {
        return emergencyContactRepository.findAll();
    }

    @Override
    public List<EmergencyContact> findAllByUser(Users user) {
        return emergencyContactRepository.findAllByUser(user);
    }

    @Override
    public List<EmergencyContact> findAllByUserAndContactType(Users user, Integer type) {
        return emergencyContactRepository.findAllByUserAndContactType(user, type);
    }

	@Override
	public List<EmergencyContact> verifierNumeroExiste(String contact_type, Long id, String phone) {
		// TODO Auto-generated method stub
		return emergencyContactRepository.verifierNumeroExiste(contact_type, id, phone);
	}

	@Override
	public boolean deleteContact(Long id) {
		emergencyContactRepository.deleteById(id);
		return true;
	}

}
