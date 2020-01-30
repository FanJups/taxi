package biz.advanceitgroup.taxirideserver.profiles.services.impl;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.UserPaymentMode;
import biz.advanceitgroup.taxirideserver.profiles.repositories.UserPaymentModeRepository;
import biz.advanceitgroup.taxirideserver.profiles.services.interfaces.UserPaymentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPaymentModeServiceImpl implements UserPaymentModeService {

    @Autowired
    UserPaymentModeRepository userPaymentModeRepository;


    @Override
    public UserPaymentMode save(UserPaymentMode paymentMode) {
        return userPaymentModeRepository.save(paymentMode);
    }

    @Override
    public void delete(UserPaymentMode paymentMode) {
        userPaymentModeRepository.delete(paymentMode);
    }

    @Override
    public Optional<UserPaymentMode> findOne(String id) {
        return userPaymentModeRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<UserPaymentMode> findOne(Long id) {
        return userPaymentModeRepository.findById(id);
    }

    @Override
    public UserPaymentMode update(UserPaymentMode paymentMode, Long id) {
        paymentMode.setId(id);
        return save(paymentMode);
    }

    @Override
    public List<UserPaymentMode> findAll() {
        return userPaymentModeRepository.findAll();
    }

    @Override
    public List<UserPaymentMode> findAllByUser(Users user) {
        return userPaymentModeRepository.findAllByUser(user);
    }
    
    @Override
	public Boolean deletePayment(Long id, int paymentnumber) {
    	userPaymentModeRepository.deletePayement(id, paymentnumber);
		return true;
	}

	@Override
	public Boolean defaultPayment(Long id, int paymentnumber) {
		userPaymentModeRepository.initdefaultPayment(id);
		userPaymentModeRepository.defaultPayment(id, paymentnumber);
		return true;
	}

	@Override
	public Optional<UserPaymentMode> findDefaultPayment(Long user_id) {
		return userPaymentModeRepository.findDefaultPayment(user_id);
	}
}
